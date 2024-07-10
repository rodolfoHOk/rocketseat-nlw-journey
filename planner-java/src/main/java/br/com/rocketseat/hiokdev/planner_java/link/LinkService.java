package br.com.rocketseat.hiokdev.planner_java.link;

import br.com.rocketseat.hiokdev.planner_java.trip.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;

    public LinkResponse registerLink(LinkRequestPayload payload, Trip trip) {
        Link newLink = new Link(payload.title(), payload.url(), trip);
        this.linkRepository.save(newLink);
        return new LinkResponse(newLink.getId());
    }

    public List<LinkData> getAllLinksByTripId(UUID tripId) {
        return this.linkRepository.findByTripId(tripId).stream()
                .map(link -> new LinkData(link.getId(), link.getTitle(), link.getUrl()))
                .toList();
    }

}
