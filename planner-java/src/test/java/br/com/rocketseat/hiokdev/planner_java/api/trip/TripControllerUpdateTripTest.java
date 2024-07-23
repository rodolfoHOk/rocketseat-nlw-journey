package br.com.rocketseat.hiokdev.planner_java.api.trip;

import br.com.rocketseat.hiokdev.planner_java.api.trip.dto.TripUpdateRequestPayload;
import br.com.rocketseat.hiokdev.planner_java.domain.activity.ActivityService;
import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.NotFoundException;
import br.com.rocketseat.hiokdev.planner_java.domain.link.LinkService;
import br.com.rocketseat.hiokdev.planner_java.domain.participant.ParticipantService;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripQueryService;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripService;
import br.com.rocketseat.hiokdev.planner_java.factory.domain.TripFactory;
import br.com.rocketseat.hiokdev.planner_java.factory.dto.TripUpdateRequestPayloadFactory;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(TripController.class)
public class TripControllerUpdateTripTest {

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
    void shouldReturnNoContentWhenUpdateTrip() throws Exception {
        var tripId = UUID.randomUUID();
        var trip = TripFactory.getTripWithId(tripId);
        when(tripService.update(any(), any())).thenReturn(trip);

        var payload = TripUpdateRequestPayloadFactory.getPayload();
        mockMvc.perform(put("/trips/" + tripId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody(payload)))
                .andExpect(status().isNoContent());
    }

    private static Stream<Arguments> providesTripUpdateRequestPayloads() {
        return Stream.of(
                Arguments.of(TripUpdateRequestPayloadFactory.getPayloadWithBlankDestination(), "destination"),
                Arguments.of(TripUpdateRequestPayloadFactory.getPayloadWithBlankStartsAt(), "starts_at"),
                Arguments.of(TripUpdateRequestPayloadFactory.getPayloadWithBlankEndsAt(), "ends_at"),
                Arguments.of(TripUpdateRequestPayloadFactory.getPayloadWithShortDestination(), "destination"),
                Arguments.of(TripUpdateRequestPayloadFactory.getPayloadWithLongDestination(), "destination"),
                Arguments.of(TripUpdateRequestPayloadFactory.getPayloadWithInvalidStartsAt(), "starts_at"),
                Arguments.of(TripUpdateRequestPayloadFactory.getPayloadWithInvalidEndsAt(), "ends_at")
        );
    }

    @MethodSource("providesTripUpdateRequestPayloads")
    @ParameterizedTest
    void shouldReturnBadRequestAndFieldWithProblemWhenUpdateTripWithInvalidPayload(TripUpdateRequestPayload payload, String fieldName) throws Exception {
        var tripId = UUID.randomUUID();

        mockMvc.perform(put("/trips/" + tripId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody(payload)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value("/trips/" + tripId))
                .andExpect(jsonPath("$.fields[0].name").value(fieldName));
    }

    @Test
    void shouldReturnBadRequestWhenUpdateTripWithInvalidUuid() throws Exception {
        var tripId = "invalid-uuid";

        var payload = TripUpdateRequestPayloadFactory.getPayload();
        mockMvc.perform(put("/trips/" + tripId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody(payload)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value("/trips/" + tripId));
    }

    @Test
    void shouldReturnNotFoundWhenUpdateTripWithNonExistingId() throws Exception {
        var nonExistingTripId = UUID.randomUUID();
        doThrow(NotFoundException.class).when(tripService).update(any(), any());

        var payload = TripUpdateRequestPayloadFactory.getPayload();
        mockMvc.perform(put("/trips/" + nonExistingTripId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody(payload)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.path").value("/trips/" + nonExistingTripId));
    }

}
