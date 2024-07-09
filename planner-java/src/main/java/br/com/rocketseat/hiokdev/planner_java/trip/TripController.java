package br.com.rocketseat.hiokdev.planner_java.trip;

import br.com.rocketseat.hiokdev.planner_java.participant.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripRepository tripRepository;

    private final ParticipantService participantService;

    @PostMapping
    public ResponseEntity<TripCreateResponse> create(@RequestBody TripRequestPayload payload) {
        var newTrip = new Trip(payload);
        newTrip = this.tripRepository.save(newTrip);
        this.participantService.registerParticipantsToTrip(payload.emails_to_invite(), newTrip.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new TripCreateResponse(newTrip.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id){
        Optional<Trip> trip = this.tripRepository.findById(id);
        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
