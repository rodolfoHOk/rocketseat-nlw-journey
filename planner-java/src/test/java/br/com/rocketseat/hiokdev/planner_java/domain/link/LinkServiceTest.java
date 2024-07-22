package br.com.rocketseat.hiokdev.planner_java.domain.link;

import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripQueryService;
import br.com.rocketseat.hiokdev.planner_java.factory.domain.LinkFactory;
import br.com.rocketseat.hiokdev.planner_java.factory.domain.TripFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class LinkServiceTest {

    @Mock
    private LinkRepository linkRepository;

    @Mock
    private TripQueryService tripQueryService;

    @InjectMocks
    private LinkService linkService;

    @Test
    void shouldReturnLinkWhenRegisterLink() {
        var tripId = UUID.randomUUID();
        var trip = TripFactory.getTripWithId(tripId);
        when(tripQueryService.getById(tripId)).thenReturn(trip);
        when(linkRepository.save(any())).thenAnswer(invocationOnMock -> {
            var link = invocationOnMock.getArgument(0, Link.class);
            link.setId(UUID.randomUUID());
            return link;
        });

        var link = LinkFactory.getLinkWithoutId();
        var sutLink = linkService.registerLink(link, tripId);

        assertThat(sutLink.getId()).isNotNull();
        assertThat(sutLink.getTitle()).isEqualTo(link.getTitle());
        assertThat(sutLink.getUrl()).isEqualTo(link.getUrl());
        assertThat(sutLink.getTrip()).isEqualTo(trip);
        verify(tripQueryService).getById(any());
        verify(linkRepository).save(any());
    }

    @Test
    void shouldReturnLinksListWhenGetAllLinksByTripId() {
        var tripId = UUID.randomUUID();
        var links = LinkFactory.getLinkList(tripId);
        when(linkRepository.findByTripId(tripId)).thenReturn(links);

        var sutLinks = linkService.getAllLinksByTripId(tripId);

        assertThat(sutLinks).usingRecursiveComparison().isEqualTo(links);
        verify(linkRepository).findByTripId(any());
    }

}
