package br.com.rocketseat.hiokdev.planner_java.api.trip;

import br.com.rocketseat.hiokdev.planner_java.api.link.dto.LinkRequestPayload;
import br.com.rocketseat.hiokdev.planner_java.domain.activity.ActivityService;
import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.NotFoundException;
import br.com.rocketseat.hiokdev.planner_java.domain.link.LinkService;
import br.com.rocketseat.hiokdev.planner_java.domain.participant.ParticipantService;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripQueryService;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripService;
import br.com.rocketseat.hiokdev.planner_java.factory.domain.LinkFactory;
import br.com.rocketseat.hiokdev.planner_java.factory.dto.LinkRequestPayloadFactory;
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
public class TripControllerRegisterLinkTest {

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
    void shouldReturnCreatedAndActivityIdWhenRegisterLink() throws Exception {
        var tripId = UUID.randomUUID();
        var linkId = UUID.randomUUID();
        var link = LinkFactory.getLinkWithTripIdAndId(tripId, linkId);
        when(linkService.registerLink(any(), any())).thenReturn(link);

        var payload = LinkRequestPayloadFactory.getPayload();
        mockMvc.perform(post("/trips/" + tripId + "/links")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.linkId").value(linkId.toString()));
    }

    private static Stream<Arguments> providesLinkRequestPayloads() {
        return Stream.of(
                Arguments.of(LinkRequestPayloadFactory.getPayloadWithBlankTitle(), "title"),
                Arguments.of(LinkRequestPayloadFactory.getPayloadWithBlankUrl(), "url"),
                Arguments.of(LinkRequestPayloadFactory.getPayloadWithShortTitle(), "title"),
                Arguments.of(LinkRequestPayloadFactory.getPayloadWithLongTitle(), "title"),
                Arguments.of(LinkRequestPayloadFactory.getPayloadWithInvalidUrl(), "url")
        );
    }

    @MethodSource("providesLinkRequestPayloads")
    @ParameterizedTest
    void shouldReturnBadRequestAndFieldWithProblemWhenRegisterLinkWithInvalidPayload(LinkRequestPayload payload, String fieldName) throws Exception {
        var tripId = UUID.randomUUID();

        mockMvc.perform(post("/trips/" + tripId + "/links")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody(payload)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value("/trips/" + tripId + "/links"))
                .andExpect(jsonPath("$.fields[0].name").value(fieldName));
    }

    @Test
    void shouldReturnBadRequestWhenRegisterLinkWithInvalidUuid() throws Exception {
        var tripId = "invalid-uuid";

        var payload = LinkRequestPayloadFactory.getPayload();
        mockMvc.perform(post("/trips/" + tripId + "/links")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody(payload)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value("/trips/" + tripId + "/links"));
    }

    @Test
    void shouldReturnNotFoundWhenRegisterLinkWithNonExistingTripId() throws Exception {
        var nonExistingTripId = UUID.randomUUID();
        doThrow(NotFoundException.class).when(linkService).registerLink(any(), any());

        var payload = LinkRequestPayloadFactory.getPayload();
        mockMvc.perform(post("/trips/" + nonExistingTripId + "/links")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody(payload)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.path").value("/trips/" + nonExistingTripId + "/links"));
    }

}
