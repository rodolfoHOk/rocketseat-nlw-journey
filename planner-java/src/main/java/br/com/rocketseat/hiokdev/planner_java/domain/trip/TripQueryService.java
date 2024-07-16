package br.com.rocketseat.hiokdev.planner_java.domain.trip;

import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TripQueryService {

    private final TripRepository tripRepository;

    public Trip getById(UUID id) {
        return this.tripRepository.findById(id).orElseThrow(() -> new NotFoundException("Trip not found"));
    }

}
