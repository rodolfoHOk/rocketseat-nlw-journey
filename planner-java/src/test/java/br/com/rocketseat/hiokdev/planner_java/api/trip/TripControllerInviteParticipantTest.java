package br.com.rocketseat.hiokdev.planner_java.api.trip;

import br.com.rocketseat.hiokdev.planner_java.api.participant.dto.InviteParticipantRequestPayload;
import br.com.rocketseat.hiokdev.planner_java.domain.activity.ActivityService;
import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.NotFoundException;
import br.com.rocketseat.hiokdev.planner_java.domain.link.LinkService;
import br.com.rocketseat.hiokdev.planner_java.domain.participant.ParticipantService;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripQueryService;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripService;
import br.com.rocketseat.hiokdev.planner_java.factory.domain.ParticipantFactory;
import br.com.rocketseat.hiokdev.planner_java.factory.dto.InviteParticipantRequestPayloadFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(TripController.class)
public class TripControllerInviteParticipantTest {

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

    private String requestBody(Object payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Test
    void shouldReturnCreatedAndParticipantIdWhenInviteParticipant() throws Exception {
        var tripId = UUID.randomUUID();
        var participantId = UUID.randomUUID();
        var participant = ParticipantFactory.getParticipantWithTripIdAndId(tripId, participantId);
        when(participantService.registerParticipantToTrip(any(), any())).thenReturn(participant);

        var payload = InviteParticipantRequestPayloadFactory.getPayload();
        mockMvc.perform(post("/trips/" + tripId + "/invites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.participantId").value(participantId.toString()));
    }

    private static Stream<Arguments> providesInviteParticipantRequestPayloads() {
        return Stream.of(
                Arguments.of(InviteParticipantRequestPayloadFactory.getPayloadWithBlankEmail(), "email"),
                Arguments.of(InviteParticipantRequestPayloadFactory.getPayloadWithInvalidEmail(), "email")
        );
    }

    @MethodSource("providesInviteParticipantRequestPayloads")
    @ParameterizedTest
    void shouldReturnBadRequestAndFieldWithProblemWhenInviteParticipantWithInvalidPayload(InviteParticipantRequestPayload payload, String fieldName) throws Exception {
        var tripId = UUID.randomUUID();

        mockMvc.perform(post("/trips/" + tripId + "/invites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody(payload)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value("/trips/" + tripId + "/invites"))
                .andExpect(jsonPath("$.fields[0].name").value(fieldName));
    }

    @Test
    void shouldReturnBadRequestWhenInviteParticipantWithInvalidUuid() throws Exception {
        var tripId = "invalid-uuid";

        var payload = InviteParticipantRequestPayloadFactory.getPayload();
        mockMvc.perform(post("/trips/" + tripId + "/invites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody(payload)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value("/trips/" + tripId + "/invites"));
    }

    @Test
    void shouldReturnNotFoundWhenInviteParticipantWithNonExistingTripId() throws Exception {
        var nonExistingTripId = UUID.randomUUID();
        doThrow(NotFoundException.class).when(participantService).registerParticipantToTrip(any(), any());

        var payload = InviteParticipantRequestPayloadFactory.getPayload();
        mockMvc.perform(post("/trips/" + nonExistingTripId + "/invites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody(payload)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.path").value("/trips/" + nonExistingTripId + "/invites"));
    }

}
