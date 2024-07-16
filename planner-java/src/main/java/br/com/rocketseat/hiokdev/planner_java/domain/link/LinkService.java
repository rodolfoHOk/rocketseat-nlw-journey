package br.com.rocketseat.hiokdev.planner_java.domain.link;

import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;
    private final TripQueryService tripQueryService;

    public Link registerLink(Link link, UUID tripId) {
        var trip = this.tripQueryService.getById(tripId);
        link.setTrip(trip);
        return this.linkRepository.save(link);
    }

    public List<Link> getAllLinksByTripId(UUID tripId) {
        return this.linkRepository.findByTripId(tripId);
    }

}
