package br.com.rocketseat.hiokdev.planner_java.api.trip;

import br.com.rocketseat.hiokdev.planner_java.domain.activity.ActivityService;
import br.com.rocketseat.hiokdev.planner_java.domain.link.LinkService;
import br.com.rocketseat.hiokdev.planner_java.domain.participant.ParticipantService;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripQueryService;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripService;
import br.com.rocketseat.hiokdev.planner_java.factory.domain.LinkFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(TripController.class)
public class TripControllerGetAllLinksTest {

    @MockBean
    private TripService tripService;

    @MockBean
    private TripQueryService tripQueryService;

    @MockBean
    private ParticipantService participantService;

    @MockBean
    private ActivityService activityService;

    @MockBean
    private LinkService linkService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnOkAndLinksWhenGetAllLinks() throws Exception {
        var tripId = UUID.randomUUID();
        var links = LinkFactory.getLinkList(tripId);
        when(linkService.getAllLinksByTripId(tripId)).thenReturn(links);

        mockMvc.perform(get("/trips/" + tripId + "/links"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(links.getFirst().getId().toString()))
                .andExpect(jsonPath("$[0].title").value(links.getFirst().getTitle()))
                .andExpect(jsonPath("$[0].url").value(links.getFirst().getUrl()));
    }

    @Test
    void shouldReturnBadRequestWhenGetAllLinksWithInvalidUuid() throws Exception {
        var tripId = "invalid-uuid";

        mockMvc.perform(get("/trips/" + tripId + "/links"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value("/trips/" + tripId + "/links"));
    }

}
