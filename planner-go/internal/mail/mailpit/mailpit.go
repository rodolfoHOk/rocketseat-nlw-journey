package mailpit

import (
	"context"
	"fmt"
	"time"

	"github.com/google/uuid"
	"github.com/jackc/pgx/v5/pgxpool"
	"github.com/rodolfoHOk/rocketseat.nlw-journey/planner-go/internal/pgstore"
	"github.com/wneessen/go-mail"
)

type store interface {
	GetTrip(context.Context, uuid.UUID) (pgstore.GetTripRow, error)
}

type Mailpit struct {
	store store
}

func NewMailPit(pool *pgxpool.Pool) Mailpit {
	return Mailpit{
		pgstore.New(pool),
	}
}

func (mp Mailpit) SendConfirmTripEmailToTripOwner(tripID uuid.UUID) error {
	ctx := context.Background()
	trip, err := mp.store.GetTrip(ctx, tripID)
	if err != nil {
		return fmt.Errorf("mailpit: failed to get trip for SendConfirmTripEmailToTripOwner: %w", err)
	}

	msg := mail.NewMsg()
	if err := msg.From("mailpit@journey.com"); err != nil {
		return fmt.Errorf("mailpit: failed to set 'from' in email SendConfirmTripEmailToTripOwner: %w", err)
	}
	if err := msg.To(trip.OwnerEmail); err != nil {
		return fmt.Errorf("mailpit: failed to set 'to' in email SendConfirmTripEmailToTripOwner: %w", err)
	}
	msg.Subject("Confirme sua viagem")
	confirmationLink := fmt.Sprintf(
		`%s/trips/%s/confirm`,
		"http://localhost:8080", trip.ID.String())
	msg.SetBodyString(mail.TypeTextPlain, fmt.Sprintf(`
		Olá, %s!

		A sua viagem para %s que começa no dia %s precisa ser confirmada.
		Acesse o link abaixo para confirmar:

		%s
		`,
		trip.OwnerName, trip.Destination, trip.StartsAt.Time.Format(time.DateOnly), confirmationLink,
	))

	client, err := mail.NewClient("localhost", mail.WithTLSPortPolicy(mail.NoTLS), mail.WithPort(1025))
	if err != nil {
		return fmt.Errorf("mailpit: failed create email client SendConfirmTripEmailToTripOwner: %w", err)
	}

	if err := client.DialAndSend(msg); err != nil {
		return fmt.Errorf("mailpit: failed send email client SendConfirmTripEmailToTripOwner: %w", err)
	}

	return nil
}

func (mp Mailpit) SendEmailToInviteToTheTrip(trip pgstore.GetTripRow, participant pgstore.GetParticipantsRow) error {
	msg := mail.NewMsg()
	if err := msg.From("mailpit@journey.com"); err != nil {
		return fmt.Errorf("mailpit: failed to set 'from' in email SendEmailToInviteToTheTrip: %w", err)
	}
	if err := msg.To(participant.Email); err != nil {
		return fmt.Errorf("mailpit: failed to set 'to' in email SendEmailToInviteToTheTrip: %w", err)
	}
	msg.Subject(fmt.Sprintf(`Confirme sua presença na viagem para %s em %s`,
		trip.Destination, trip.StartsAt.Time.Format(time.DateOnly)))
	confirmationLink := fmt.Sprintf(
		`%s/trips/%s?participant=%s`,
		"http://localhost:5173", trip.ID.String(), participant.ID.String(),
	)
	confirmationApp := fmt.Sprintf(
		`planner://trip/%s?participant=%s`,
		trip.ID.String(), participant.ID.String(),
	)
	msg.SetBodyString(mail.TypeTextPlain, fmt.Sprintf(`
		Você foi convidado(a) para participar de uma viagem para %s nas datas de %s até %s.
		
		Para confirmar sua presença na viagem, acesse no seu navegador web favorito o link abaixo:

		%s

		ou abra no app através deste link:

		%s

		Caso você não saiba do que se trata esse e-mail, apenas ignore esse e-mail.
		`,
		trip.Destination, trip.StartsAt.Time.Format(time.DateOnly), trip.EndsAt.Time.Format(time.DateOnly),
		confirmationLink, confirmationApp,
	))

	client, err := mail.NewClient("localhost", mail.WithTLSPortPolicy(mail.NoTLS), mail.WithPort(1025))
	if err != nil {
		return fmt.Errorf("mailpit: failed create email client SendEmailToInviteToTheTrip: %w", err)
	}

	if err := client.DialAndSend(msg); err != nil {
		return fmt.Errorf("mailpit: failed send email client SendEmailToInviteToTheTrip: %w", err)
	}

	return nil
}
