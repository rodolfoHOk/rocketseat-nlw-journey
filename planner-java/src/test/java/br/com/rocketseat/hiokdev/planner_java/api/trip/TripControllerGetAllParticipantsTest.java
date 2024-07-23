package br.com.rocketseat.hiokdev.planner_java.api.trip;

import br.com.rocketseat.hiokdev.planner_java.domain.activity.ActivityService;
import br.com.rocketseat.hiokdev.planner_java.domain.link.LinkService;
import br.com.rocketseat.hiokdev.planner_java.domain.participant.ParticipantService;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripQueryService;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripService;
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
public class TripControllerGetAllParticipantsTest {

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
    void shouldReturnOkAndParticipantsWhenGetAllParticipants() throws Exception {
        var tripId = UUID.randomUUID();
        var trip = TripFactory.getTripWithId(tripId);
        var participants = new ArrayList<>(ParticipantFactory.getParticipantsFromInvites(EmailFactory.getEmailList(), trip));
        participants.add(ParticipantFactory.getOwnerParticipant(trip));
        when(participantService.getAllParticipantsByTripId(tripId)).thenReturn(participants);

        mockMvc.perform(get("/trips/" + tripId + "/participants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id").value(participants.getFirst().getId().toString()))
                .andExpect(jsonPath("$[0].name").value(participants.getFirst().getName()))
                .andExpect(jsonPath("$[0].email").value(participants.getFirst().getEmail()))
                .andExpect(jsonPath("$[0].is_confirmed").value(participants.getFirst().getIsConfirmed()));
    }

    @Test
    void shouldReturnBadRequestWhenGetAllParticipantsWithInvalidUuid() throws Exception {
        var tripId = "invalid-uuid";

        mockMvc.perform(get("/trips/" + tripId + "/participants"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value("/trips/" + tripId + "/participants"));
    }

}
