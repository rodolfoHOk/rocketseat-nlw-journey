package br.com.rocketseat.hiokdev.planner_java.trip;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripRepository tripRepository;

    @PostMapping
    public ResponseEntity<TripCreateResponse> create(@RequestBody TripRequestPayload payload) {
        Trip newTrip = new Trip(payload);
        this.tripRepository.save(newTrip);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TripCreateResponse(newTrip.getId()));
    }

}
