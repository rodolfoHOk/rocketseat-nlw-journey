package api

import (
	"context"
	"encoding/json"
	"errors"
	"net/http"
	"sort"
	"time"

	"github.com/discord-gophers/goapi-gen/types"
	"github.com/go-playground/validator/v10"
	"github.com/google/uuid"
	"github.com/jackc/pgx/v5"
	"github.com/jackc/pgx/v5/pgtype"
	"github.com/jackc/pgx/v5/pgxpool"
	"github.com/rodolfoHOk/rocketseat.nlw-journey/planner-go/internal/api/spec"
	"github.com/rodolfoHOk/rocketseat.nlw-journey/planner-go/internal/pgstore"
	"go.uber.org/zap"
)

type mailer interface {
	SendConfirmTripEmailToTripOwner(uuid.UUID) error
	SendEmailToInviteToTheTrip(pgstore.GetTripRow, pgstore.GetParticipantsRow) error
}

type store interface {
	ConfirmParticipant(context.Context, pgstore.ConfirmParticipantParams) error
	CreateActivity(context.Context, pgstore.CreateActivityParams) (uuid.UUID, error)
	CreateTrip(context.Context, *pgxpool.Pool, spec.CreateTripRequest) (uuid.UUID, error)
	CreateTripLink(context.Context, pgstore.CreateTripLinkParams) (uuid.UUID, error)
	GetParticipant(context.Context, uuid.UUID) (pgstore.GetParticipantRow, error)
	GetParticipants(context.Context, uuid.UUID) ([]pgstore.GetParticipantsRow, error)
	GetTrip(context.Context, uuid.UUID) (pgstore.GetTripRow, error)
	GetTripActivities(context.Context, uuid.UUID) ([]pgstore.GetTripActivitiesRow, error)
	GetTripLinks(context.Context, uuid.UUID) ([]pgstore.GetTripLinksRow, error)
	// InviteParticipantsToTrip(context.Context, []pgstore.InviteParticipantsToTripParams) (int64, error)
	UpdateTrip(context.Context, pgstore.UpdateTripParams) error
}

type API struct {
	store     store
	logger    *zap.Logger
	validator *validator.Validate
	pool      *pgxpool.Pool
	mailer    mailer
}

func NewApi(pool *pgxpool.Pool, logger *zap.Logger, mailer mailer) API {
	validator := validator.New(validator.WithRequiredStructEnabled())
	return API{
		pgstore.New(pool),
		logger,
		validator,
		pool,
		mailer,
	}
}

// Confirms a participant on a trip.
// (PATCH /participants/{participantId}/confirm)
func (api API) PatchParticipantsParticipantIDConfirm(w http.ResponseWriter, r *http.Request, participantID string) *spec.Response {
	id, err := uuid.Parse(participantID)
	if err != nil {
		return spec.PatchParticipantsParticipantIDConfirmJSON400Response(spec.Error{Message: "invalid uuid"})
	}
	participant, err := api.store.GetParticipant(r.Context(), id)
	if err != nil {
		if errors.Is(err, pgx.ErrNoRows) {
			return spec.PatchParticipantsParticipantIDConfirmJSON400Response(spec.Error{Message: "participant not found"})
		}
		api.logger.Error("failed to get participant", zap.Error(err), zap.String("participant_id", participantID))
		return spec.PatchParticipantsParticipantIDConfirmJSON400Response(spec.Error{Message: "something went wrong, try again"})
	}
	if participant.IsConfirmed {
		return spec.PatchParticipantsParticipantIDConfirmJSON400Response(spec.Error{Message: "participant already confirmed"})
	}
	var body spec.ConfirmParticipantRequest
	if err = json.NewDecoder(r.Body).Decode(&body); err != nil {
		spec.PatchParticipantsParticipantIDConfirmJSON400Response(spec.Error{Message: "invalid json: " + err.Error()})
	}
	if err = api.store.ConfirmParticipant(r.Context(), pgstore.ConfirmParticipantParams{Name: body.Name, ID: participant.ID}); err != nil {
		api.logger.Error("failed to confirm participant", zap.Error(err), zap.String("participant_id", participantID))
		return spec.PatchParticipantsParticipantIDConfirmJSON400Response(spec.Error{Message: "something went wrong, try again"})
	}
	return spec.PatchParticipantsParticipantIDConfirmJSON204Response(nil)
}

// Create a new trip
// (POST /trips)
func (api API) PostTrips(w http.ResponseWriter, r *http.Request) *spec.Response {
	var body spec.CreateTripRequest
	if err := json.NewDecoder(r.Body).Decode(&body); err != nil {
		spec.PostTripsJSON400Response(spec.Error{Message: "invalid json: " + err.Error()})
	}
	if err := api.validator.Struct(body); err != nil {
		return spec.PostTripsJSON400Response(spec.Error{Message: "invalid input: " + err.Error()})
	}
	tripID, err := api.store.CreateTrip(r.Context(), api.pool, body)
	if err != nil {
		println(err.Error())
		return spec.PostTripsJSON400Response(spec.Error{Message: "failed to create trip, try again"})
	}
	go func() {
		if err := api.mailer.SendConfirmTripEmailToTripOwner(tripID); err != nil {
			api.logger.Error(
				"failed to send email on PostTrips",
				zap.Error(err),
				zap.String("trip_id", tripID.String()),
			)
		}
	}()
	return spec.PostTripsJSON201Response(spec.CreateTripResponse{TripID: tripID.String()})
}

// Get a trip details.
// (GET /trips/{tripId})
func (api API) GetTripsTripID(w http.ResponseWriter, r *http.Request, tripID string) *spec.Response {
	trip, err := api.getTripById(r.Context(), tripID)
	if err != nil {
		return spec.GetTripsTripIDJSON400Response(spec.Error{Message: err.Error()})
	}
	return spec.GetTripsTripIDJSON200Response(spec.GetTripDetailsResponse{Trip: spec.GetTripDetailsResponseTripObj{
		ID:          trip.ID.String(),
		Destination: trip.Destination,
		StartsAt:    trip.StartsAt.Time,
		EndsAt:      trip.EndsAt.Time,
		IsConfirmed: trip.IsConfirmed,
	}})
}

// Update a trip.
// (PUT /trips/{tripId})
func (api API) PutTripsTripID(w http.ResponseWriter, r *http.Request, tripID string) *spec.Response {
	trip, err := api.getTripById(r.Context(), tripID)
	if err != nil {
		return spec.PutTripsTripIDJSON400Response(spec.Error{Message: err.Error()})
	}
	var body spec.UpdateTripRequest
	if err = json.NewDecoder(r.Body).Decode(&body); err != nil {
		return spec.PutTripsTripIDJSON400Response(spec.Error{Message: "invalid json"})
	}
	if err = api.validator.Struct(body); err != nil {
		return spec.PutTripsTripIDJSON400Response(spec.Error{Message: "invalid input: " + err.Error()})
	}
	if err = api.store.UpdateTrip(r.Context(), pgstore.UpdateTripParams{
		ID:          trip.ID,
		Destination: body.Destination,
		StartsAt:    pgtype.Timestamp{Valid: true, Time: body.StartsAt},
		EndsAt:      pgtype.Timestamp{Valid: true, Time: body.EndsAt},
		IsConfirmed: trip.IsConfirmed,
	}); err != nil {
		spec.PutTripsTripIDJSON400Response(spec.Error{Message: "something went wrong, try again"})
	}
	return spec.PutTripsTripIDJSON204Response(spec.Response{Code: 204})
}

// Get a trip activities.
// (GET /trips/{tripId}/activities)
func (api API) GetTripsTripIDActivities(w http.ResponseWriter, r *http.Request, tripID string) *spec.Response {
	trip, err := api.getTripById(r.Context(), tripID)
	if err != nil {
		return spec.GetTripsTripIDActivitiesJSON400Response(spec.Error{Message: err.Error()})
	}
	activities, err := api.store.GetTripActivities(r.Context(), trip.ID)
	if err != nil {
		return spec.GetTripsTripIDActivitiesJSON400Response(spec.Error{Message: "something went wrong, try again"})
	}
	sort.SliceStable(activities, func(i, j int) bool {
		return activities[i].OccursAt.Time.Before(activities[j].OccursAt.Time)
	})
	format := "2006-02-01"
	var responseBody []spec.GetTripActivitiesResponseOuterArray
	var lastDate time.Time = activities[0].OccursAt.Time
	innerResponse := make([]spec.GetTripActivitiesResponseInnerArray, 0)
	for _, activity := range activities {
		if lastDate.Format(format) == activity.OccursAt.Time.Format(format) {
			innerResponse = append(innerResponse, spec.GetTripActivitiesResponseInnerArray{
				ID:       activity.ID.String(),
				Title:    activity.Title,
				OccursAt: activity.OccursAt.Time,
			})
		} else {
			responseBody = append(responseBody, spec.GetTripActivitiesResponseOuterArray{
				Date:       lastDate,
				Activities: innerResponse,
			})
			innerResponse = make([]spec.GetTripActivitiesResponseInnerArray, 0)
			lastDate = activity.OccursAt.Time
			innerResponse = append(innerResponse, spec.GetTripActivitiesResponseInnerArray{
				ID:       activity.ID.String(),
				Title:    activity.Title,
				OccursAt: activity.OccursAt.Time,
			})
		}
	}
	responseBody = append(responseBody, spec.GetTripActivitiesResponseOuterArray{
		Date:       lastDate,
		Activities: innerResponse,
	})
	return spec.GetTripsTripIDActivitiesJSON200Response(spec.GetTripActivitiesResponse{Activities: responseBody})
}

// Create a trip activity.
// (POST /trips/{tripId}/activities)
func (api API) PostTripsTripIDActivities(w http.ResponseWriter, r *http.Request, tripID string) *spec.Response {
	trip, err := api.getTripById(r.Context(), tripID)
	if err != nil {
		return spec.PostTripsTripIDActivitiesJSON400Response(spec.Error{Message: err.Error()})
	}
	var requestBody spec.CreateActivityRequest
	if err = json.NewDecoder(r.Body).Decode(&requestBody); err != nil {
		return spec.PostTripsTripIDActivitiesJSON400Response(spec.Error{Message: "json invalid"})
	}
	if err = api.validator.Struct(requestBody); err != nil {
		return spec.PostTripsTripIDActivitiesJSON400Response(spec.Error{Message: "invalid input: " + err.Error()})
	}
	activityId, err := api.store.CreateActivity(r.Context(), pgstore.CreateActivityParams{
		TripID:   trip.ID,
		Title:    requestBody.Title,
		OccursAt: pgtype.Timestamp{Valid: true, Time: requestBody.OccursAt},
	})
	if err != nil {
		return spec.PostTripsTripIDActivitiesJSON400Response(spec.Error{Message: "something went wrong, try again"})
	}
	return spec.PostTripsTripIDActivitiesJSON201Response(spec.CreateActivityResponse{
		ActivityID: activityId.String(),
	})
}

// Confirm a trip and send e-mail invitations.
// (GET /trips/{tripId}/confirm)
func (api API) GetTripsTripIDConfirm(w http.ResponseWriter, r *http.Request, tripID string) *spec.Response {
	trip, err := api.getTripById(r.Context(), tripID)
	if err != nil {
		return spec.GetTripsTripIDConfirmJSON400Response(spec.Error{Message: err.Error()})
	}
	err = api.store.UpdateTrip(r.Context(), pgstore.UpdateTripParams{
		ID:          trip.ID,
		Destination: trip.Destination,
		StartsAt:    trip.StartsAt,
		EndsAt:      trip.EndsAt,
		IsConfirmed: true,
	})
	if err != nil {
		return spec.GetTripsTripIDConfirmJSON400Response(spec.Error{Message: "something went wrong, try again"})
	}
	participants, err := api.store.GetParticipants(r.Context(), trip.ID)
	if err != nil {
		return spec.GetTripsTripIDConfirmJSON400Response(spec.Error{Message: "something went wrong, try again"})
	}

	for _, participant := range participants {
		if participant.Email != trip.OwnerEmail {
			go api.sendEmail(*trip, participant)
		}
	}
	return spec.GetTripsTripIDConfirmJSON204Response(spec.Response{Code: 204})
}

// Invite someone to the trip.
// (POST /trips/{tripId}/invites)
func (api API) PostTripsTripIDInvites(w http.ResponseWriter, r *http.Request, tripID string) *spec.Response {
	panic("not implemented") // TODO: Implement
}

// Get a trip links.
// (GET /trips/{tripId}/links)
func (api API) GetTripsTripIDLinks(w http.ResponseWriter, r *http.Request, tripID string) *spec.Response {
	trip, err := api.getTripById(r.Context(), tripID)
	if err != nil {
		return spec.GetTripsTripIDLinksJSON400Response(spec.Error{Message: err.Error()})
	}
	links, err := api.store.GetTripLinks(r.Context(), trip.ID)
	if err != nil {
		return spec.GetTripsTripIDLinksJSON400Response(spec.Error{Message: "something went wrong, try again"})
	}
	var responseBody []spec.GetLinksResponseArray
	for _, link := range links {
		responseBody = append(responseBody, spec.GetLinksResponseArray{
			ID:    link.ID.String(),
			Title: link.Title,
			URL:   link.Url,
		})
	}
	return spec.GetTripsTripIDLinksJSON200Response(spec.GetLinksResponse{Links: responseBody})
}

// Create a trip link.
// (POST /trips/{tripId}/links)
func (api API) PostTripsTripIDLinks(w http.ResponseWriter, r *http.Request, tripID string) *spec.Response {
	trip, err := api.getTripById(r.Context(), tripID)
	if err != nil {
		return spec.PostTripsTripIDLinksJSON400Response(spec.Error{Message: err.Error()})
	}
	var requestBody spec.CreateLinkRequest
	if err = json.NewDecoder(r.Body).Decode(&requestBody); err != nil {
		return spec.PostTripsTripIDLinksJSON400Response(spec.Error{Message: "invalid json"})
	}
	if err = api.validator.Struct(requestBody); err != nil {
		return spec.PostTripsTripIDLinksJSON400Response(spec.Error{Message: "invalid input: " + err.Error()})
	}
	linkId, err := api.store.CreateTripLink(r.Context(), pgstore.CreateTripLinkParams{
		TripID: trip.ID,
		Title:  requestBody.Title,
		Url:    requestBody.URL,
	})
	if err != nil {
		return spec.PostTripsTripIDLinksJSON400Response(spec.Error{Message: "something went wrong, try again"})
	}
	return spec.PostTripsTripIDLinksJSON201Response(spec.CreateLinkResponse{LinkID: linkId.String()})
}

// Get a trip participants.
// (GET /trips/{tripId}/participants)
func (api API) GetTripsTripIDParticipants(w http.ResponseWriter, r *http.Request, tripID string) *spec.Response {
	trip, err := api.getTripById(r.Context(), tripID)
	if err != nil {
		return spec.GetTripsTripIDParticipantsJSON400Response(spec.Error{Message: err.Error()})
	}
	participants, err := api.store.GetParticipants(r.Context(), trip.ID)
	if err != nil {
		return spec.GetTripsTripIDParticipantsJSON400Response(spec.Error{Message: "something went wrong, try again"})
	}
	var responseBody []spec.GetTripParticipantsResponseArray
	for _, participant := range participants {
		responseBody = append(responseBody, spec.GetTripParticipantsResponseArray{
			ID:          participant.ID.String(),
			Name:        &participant.Name,
			Email:       types.Email(participant.Email),
			IsConfirmed: participant.IsConfirmed,
		})
	}
	return spec.GetTripsTripIDParticipantsJSON200Response(spec.GetTripParticipantsResponse{Participants: responseBody})
}

func (api *API) getTripById(context context.Context, tripID string) (*pgstore.GetTripRow, error) {
	id, err := uuid.Parse(tripID)
	if err != nil {
		return nil, errors.New("invalid uuid")
	}
	trip, err := api.store.GetTrip(context, id)
	if err != nil {
		if errors.Is(err, pgx.ErrNoRows) {
			return nil, errors.New("trip not found")
		}
		api.logger.Error("failed to get trip", zap.Error(err), zap.String("trip_id", tripID))
		return nil, errors.New("something went wrong, try again")
	}
	return &trip, nil
}

func (api *API) sendEmail(trip pgstore.GetTripRow, participant pgstore.GetParticipantsRow) {
	if err := api.mailer.SendEmailToInviteToTheTrip(trip, participant); err != nil {
		api.logger.Error(
			"failed to send email on ConfirmTrip",
			zap.Error(err),
			zap.String("trip_id", trip.ID.String()),
		)
	}
}
