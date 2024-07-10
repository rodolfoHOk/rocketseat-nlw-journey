package br.com.rocketseat.hiokdev.planner_java.trip;

import br.com.rocketseat.hiokdev.planner_java.participant.ParticipantCreateResponse;
import br.com.rocketseat.hiokdev.planner_java.participant.ParticipantRequestPayload;
import br.com.rocketseat.hiokdev.planner_java.participant.ParticipantService;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripRepository tripRepository;

    private final ParticipantService participantService;

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayload payload) {
        var newTrip = new Trip(payload);
        newTrip = this.tripRepository.save(newTrip);
        this.participantService.registerParticipantsToTrip(payload.emails_to_invite(), newTrip);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TripCreateResponse(newTrip.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id){
        Optional<Trip> trip = this.tripRepository.findById(id);
        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID id, @RequestBody TripRequestPayload payload) {
        Optional<Trip> trip = this.tripRepository.findById(id);
        if(trip.isPresent()){
            Trip rawTrip = trip.get();
            rawTrip.setDestination(payload.destination());
            rawTrip.setStartsAt(LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME));
            rawTrip.setEndsAt(LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME));
            rawTrip = this.tripRepository.save(rawTrip);
            return ResponseEntity.ok(rawTrip);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID id) {
        Optional<Trip> trip = this.tripRepository.findById(id);
        if(trip.isPresent()){
            Trip rawTrip = trip.get();
            rawTrip.setIsConfirmed(true);
            this.participantService.triggerConfirmationEmailToParticipants(id);
            rawTrip = this.tripRepository.save(rawTrip);
            return ResponseEntity.ok(rawTrip);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantCreateResponse> inviteParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestPayload payload) {
        Optional<Trip> trip = this.tripRepository.findById(id);
        if(trip.isPresent()){
            Trip rawTrip = trip.get();
            ParticipantCreateResponse participantResponse = this.participantService.registerParticipantToTrip(payload.email(), rawTrip);
            if(rawTrip.getIsConfirmed()) {
                this.participantService.triggerConfirmationEmailToParticipant(payload.email());
            }
            return ResponseEntity.ok(participantResponse);
        }
        return ResponseEntity.notFound().build();
    }

}
