package br.com.rocketseat.hiokdev.planner_java.api.trip;

import br.com.rocketseat.hiokdev.planner_java.domain.activity.ActivityService;
import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.NotFoundException;
import br.com.rocketseat.hiokdev.planner_java.domain.link.LinkService;
import br.com.rocketseat.hiokdev.planner_java.domain.participant.ParticipantService;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripQueryService;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripService;
import br.com.rocketseat.hiokdev.planner_java.factory.domain.TripFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(TripController.class)
public class TripControllerGetTripDetailsTest {

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
    void shouldReturnOkAndTripIdWhenGetTripDetails() throws Exception {
        var tripId = UUID.randomUUID();
        var trip = TripFactory.getTripWithId(tripId);
        when(tripQueryService.getById(tripId)).thenReturn(trip);

        mockMvc.perform(get("/trips/" + tripId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(trip.getId().toString()))
                .andExpect(jsonPath("$.destination").value(trip.getDestination()))
                .andExpect(jsonPath("$.starts_at").value(trip.getStartsAt().toString()))
                .andExpect(jsonPath("$.ends_at").value(trip.getEndsAt().toString()))
                .andExpect(jsonPath("$.is_confirmed").value(trip.getIsConfirmed()));
    }

    @Test
    void shouldReturnBadRequestWhenGetTripDetailsWithInvalidUuid() throws Exception {
        var tripId = "invalid-uuid";

        mockMvc.perform(get("/trips/" + tripId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value("/trips/" + tripId));
    }

    @Test
    void shouldReturnNotFoundWhenGetTripDetailsWithNonExistingId() throws Exception {
        var nonExistingTripId = UUID.randomUUID();
        doThrow(NotFoundException.class).when(tripQueryService).getById(nonExistingTripId);

        mockMvc.perform(get("/trips/" + nonExistingTripId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.path").value("/trips/" + nonExistingTripId));
    }

}
