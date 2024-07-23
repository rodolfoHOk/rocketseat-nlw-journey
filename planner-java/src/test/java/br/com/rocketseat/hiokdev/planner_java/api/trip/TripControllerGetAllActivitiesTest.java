package br.com.rocketseat.hiokdev.planner_java.api.trip;

import br.com.rocketseat.hiokdev.planner_java.domain.activity.ActivityService;
import br.com.rocketseat.hiokdev.planner_java.domain.link.LinkService;
import br.com.rocketseat.hiokdev.planner_java.domain.participant.ParticipantService;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripQueryService;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripService;
import br.com.rocketseat.hiokdev.planner_java.factory.domain.ActivityFactory;
import br.com.rocketseat.hiokdev.planner_java.factory.domain.EmailFactory;
import br.com.rocketseat.hiokdev.planner_java.factory.domain.ParticipantFactory;
import br.com.rocketseat.hiokdev.planner_java.factory.domain.TripFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(TripController.class)
public class TripControllerGetAllActivitiesTest {

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

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldReturnOkAndActivitiesWhenGetAllActivities() throws Exception {
        var tripId = UUID.randomUUID();
        var activities = ActivityFactory.getActivityList(tripId);
        when(activityService.getAllActivitiesByTripId(tripId)).thenReturn(activities);

        mockMvc.perform(get("/trips/" + tripId + "/activities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activities", hasSize(2)))
                .andExpect(jsonPath("$.activities[0].activities", hasSize(2)))
                .andExpect(jsonPath("$.activities[1].activities", hasSize(1)))
                .andExpect(jsonPath("$.activities[0].activities[0].id").value(activities.getFirst().getId().toString()))
                .andExpect(jsonPath("$.activities[0].activities[0].title").value(activities.getFirst().getTitle()))
                .andExpect(jsonPath("$.activities[0].activities[0].occurs_at").value(activities.getFirst().getOccursAt().toString()));
    }

    @Test
    void shouldReturnBadRequestWhenGetAllActivitiesWithInvalidUuid() throws Exception {
        var tripId = "invalid-uuid";

        mockMvc.perform(get("/trips/" + tripId + "/activities"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value("/trips/" + tripId + "/activities"));
    }

}
