package br.com.rocketseat.hiokdev.planner_java.api.trip;

import br.com.rocketseat.hiokdev.planner_java.api.activity.dto.ActivityData;
import br.com.rocketseat.hiokdev.planner_java.api.activity.dto.ActivityRequestPayload;
import br.com.rocketseat.hiokdev.planner_java.api.activity.dto.ActivityResponse;
import br.com.rocketseat.hiokdev.planner_java.api.trip.dto.TripCreateResponse;
import br.com.rocketseat.hiokdev.planner_java.api.trip.dto.TripCreateRequestPayload;
import br.com.rocketseat.hiokdev.planner_java.api.trip.dto.TripData;
import br.com.rocketseat.hiokdev.planner_java.api.trip.dto.TripUpdateRequestPayload;
import br.com.rocketseat.hiokdev.planner_java.domain.activity.ActivityService;
import br.com.rocketseat.hiokdev.planner_java.api.link.dto.LinkData;
import br.com.rocketseat.hiokdev.planner_java.api.link.dto.LinkRequestPayload;
import br.com.rocketseat.hiokdev.planner_java.api.link.dto.LinkResponse;
import br.com.rocketseat.hiokdev.planner_java.domain.link.LinkService;
import br.com.rocketseat.hiokdev.planner_java.api.participant.dto.ParticipantCreateResponse;
import br.com.rocketseat.hiokdev.planner_java.api.participant.dto.ParticipantData;
import br.com.rocketseat.hiokdev.planner_java.api.participant.dto.ParticipantRequestPayload;
import br.com.rocketseat.hiokdev.planner_java.domain.participant.ParticipantService;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;
    private final ParticipantService participantService;
    private final ActivityService activityService;
    private final LinkService linkService;

    // Trips endpoints

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripCreateRequestPayload payload) {
        var trip = TripCreateRequestPayload.toDomain(payload);
        trip = this.tripService.create(trip, payload.emails_to_invite());
        return ResponseEntity.status(HttpStatus.CREATED).body(new TripCreateResponse(trip.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripData> getTripDetails(@PathVariable UUID id){
        var trip = this.tripService.getById(id);
        return ResponseEntity.ok(TripData.toResponse(trip));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTrip(@PathVariable UUID id, @RequestBody TripUpdateRequestPayload payload) {
        var trip = TripUpdateRequestPayload.toDomain(payload);
        trip = this.tripService.update(id, trip);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Void> confirmTrip(@PathVariable UUID id) {
        var trip = this.tripService.confirm(id);
        return ResponseEntity.noContent().build();
    }

    // Trip participants endpoints

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantData>> getAllParticipants(@PathVariable UUID id){
        var participants = this.participantService.getAllParticipantsByTripId(id);
        return ResponseEntity.ok(participants.stream().map(ParticipantData::toResponse).toList());
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantCreateResponse> inviteParticipant(
            @PathVariable UUID id,
            @RequestBody ParticipantRequestPayload payload
    ) {
        var participant = this.participantService.registerParticipantToTrip(payload.email(), id);
        return ResponseEntity.ok(new ParticipantCreateResponse(participant.getId()));
    }

    // Trip activities endpoints

    @PostMapping("/{id}/activities")
    public ResponseEntity<ActivityResponse> registerActivity(@PathVariable UUID id, @RequestBody ActivityRequestPayload payload) {
        var trip = this.tripService.getById(id);
        var activityResponse = this.activityService.registerActivity(payload, trip);
        return ResponseEntity.ok(activityResponse);
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityData>> getAllActivities(@PathVariable UUID id){
        List<ActivityData> activityDataList = this.activityService.getAllActivitiesByTripId(id);
        return ResponseEntity.ok(activityDataList);
    }

    // Trip links endpoints

    @PostMapping("/{id}/links")
    public ResponseEntity<LinkResponse> registerLink(@PathVariable UUID id, @RequestBody LinkRequestPayload payload) {
        var trip = this.tripService.getById(id);
        var linkResponse = this.linkService.registerLink(payload, trip);
        return ResponseEntity.ok(linkResponse);
    }

    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkData>> getAllLinks(@PathVariable UUID id){
        List<LinkData> linkDataList = this.linkService.getAllLinksByTripId(id);
        return ResponseEntity.ok(linkDataList);
    }

}
