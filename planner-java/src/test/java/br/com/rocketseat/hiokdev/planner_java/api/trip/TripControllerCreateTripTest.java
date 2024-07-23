package br.com.rocketseat.hiokdev.planner_java.api.trip;

import br.com.rocketseat.hiokdev.planner_java.api.trip.dto.TripCreateRequestPayload;
import br.com.rocketseat.hiokdev.planner_java.domain.activity.ActivityService;
import br.com.rocketseat.hiokdev.planner_java.domain.link.LinkService;
import br.com.rocketseat.hiokdev.planner_java.domain.participant.ParticipantService;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripQueryService;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripService;
import br.com.rocketseat.hiokdev.planner_java.factory.domain.TripFactory;
import br.com.rocketseat.hiokdev.planner_java.factory.dto.TripCreateRequestPayloadFactory;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(TripController.class)
public class TripControllerCreateTripTest {

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
    void shouldReturnOkAnTripIdWhenCreateTrip() throws Exception {
        var tripId = UUID.randomUUID();
        var trip = TripFactory.getTripWithId(tripId);
        when(tripService.create(any(), any())).thenReturn(trip);

        var payload = TripCreateRequestPayloadFactory.getPayload();
        mockMvc.perform(post("/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tripId").value(tripId.toString()));
    }

    private static Stream<Arguments> providesTripCreateRequestPayload() {
        return Stream.of(
                Arguments.of(TripCreateRequestPayloadFactory.getPayloadWithBlankDestination(), "destination"),
                Arguments.of(TripCreateRequestPayloadFactory.getPayloadWithBlankStartsAt(), "starts_at"),
                Arguments.of(TripCreateRequestPayloadFactory.getPayloadWithBlankEndsAt(), "ends_at"),
                Arguments.of(TripCreateRequestPayloadFactory.getPayloadWithBlankOwnerName(), "owner_name"),
                Arguments.of(TripCreateRequestPayloadFactory.getPayloadWithBlankOwnerEmail(), "owner_email"),
                Arguments.of(TripCreateRequestPayloadFactory.getPayloadWithEmptyEmailsToInvite(), "emails_to_invite"),
                Arguments.of(TripCreateRequestPayloadFactory.getPayloadWithShortDestination(), "destination"),
                Arguments.of(TripCreateRequestPayloadFactory.getPayloadWithLongDestination(), "destination"),
                Arguments.of(TripCreateRequestPayloadFactory.getPayloadWithInvalidStartsAt(), "starts_at"),
                Arguments.of(TripCreateRequestPayloadFactory.getPayloadWithInvalidEndsAt(), "ends_at"),
                Arguments.of(TripCreateRequestPayloadFactory.getPayloadWithInvalidOwnerEmail(), "owner_email"),
                Arguments.of(TripCreateRequestPayloadFactory.getPayloadWithInvalidEmailsToInvite(), "emails_to_invite[2]")
        );
    }

    @MethodSource("providesTripCreateRequestPayload")
    @ParameterizedTest
    void shouldReturnBadRequestAndFieldWithProblemWhenCreateWithInvalidPayload(TripCreateRequestPayload payload, String fieldName) throws Exception {
        mockMvc.perform(post("/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody(payload)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fields[0].name").value(fieldName));
    }

}
