package br.com.rocketseat.hiokdev.planner_java.link;

import br.com.rocketseat.hiokdev.planner_java.trip.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;

    public LinkResponse registerLink(LinkRequestPayload payload, Trip trip) {
        Link newLink = new Link(payload.title(), payload.url(), trip);
        this.linkRepository.save(newLink);
        return new LinkResponse(newLink.getId());
    }

}
