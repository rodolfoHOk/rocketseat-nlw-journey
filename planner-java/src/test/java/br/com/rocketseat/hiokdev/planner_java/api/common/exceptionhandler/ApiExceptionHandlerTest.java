package br.com.rocketseat.hiokdev.planner_java.api.common.exceptionhandler;

import br.com.rocketseat.hiokdev.planner_java.api.participant.ParticipantController;
import br.com.rocketseat.hiokdev.planner_java.api.trip.TripController;
import br.com.rocketseat.hiokdev.planner_java.domain.activity.ActivityService;
import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.PlannerException;
import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.ValidationException;
import br.com.rocketseat.hiokdev.planner_java.domain.link.LinkService;
import br.com.rocketseat.hiokdev.planner_java.domain.participant.ParticipantService;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripQueryService;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripService;
import br.com.rocketseat.hiokdev.planner_java.factory.dto.ActivityRequestPayloadFactory;
import br.com.rocketseat.hiokdev.planner_java.factory.dto.ParticipantRequestPayloadFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(value = {TripController.class, ParticipantController.class})
public class ApiExceptionHandlerTest {

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
    void shouldReturnBadRequestWhenThrowValidationException() throws Exception {
        var tripId = UUID.randomUUID();
        doThrow(ValidationException.class).when(activityService).registerActivity(any(), any());

        var payload = ActivityRequestPayloadFactory.getPayloadWithOutTripDate();
        mockMvc.perform(post("/trips/" + tripId + "/activities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody(payload)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value("/trips/" + tripId + "/activities"));
    }

    @Test
    void shouldReturnInternalServerErrorWhenThrowPlannerException() throws Exception {
        var participantId = UUID.randomUUID();
        doThrow(PlannerException.class).when(participantService).confirmParticipant(any(), any());

        var payload = ParticipantRequestPayloadFactory.getPayload();
        mockMvc.perform(post("/participants/" + participantId + "/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody(payload)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.path").value("/participants/" + participantId + "/confirm"));
    }

    @Test
    void shouldReturnBadRequestWhenHasSyntaxErrorInJson() throws Exception {
        var participantId = UUID.randomUUID();

        mockMvc.perform(post("/participants/" + participantId + "/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"invalid-json-format\": \"invalid-json-format\",}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value("/participants/" + participantId + "/confirm"));
    }

    @Test
    void shouldReturnMethodNotAllowedWhenCallWithInvalidMethodInEndpoint() throws Exception {
        var participantId = UUID.randomUUID();

        mockMvc.perform(get("/participants/" + participantId + "/confirm"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.status").value(405))
                .andExpect(jsonPath("$.path").value("/participants/" + participantId + "/confirm"));
    }

    @Test
    void shouldReturnNotFoundWhenNotHaveHandlerInEndpoint() throws Exception {
        var participantId = UUID.randomUUID();

        mockMvc.perform(get("/participants/" + participantId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.path").value("/participants/" + participantId));
    }

    @Test
    void shouldReturnUnsupportedMediaTypeWhenCallWithInvalidMediaType() throws Exception {
        var participantId = UUID.randomUUID();

        mockMvc.perform(post("/participants/" + participantId + "/confirm")
                        .contentType(MediaType.IMAGE_PNG)
                        .content(""))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(jsonPath("$.status").value(415))
                .andExpect(jsonPath("$.path").value("/participants/" + participantId + "/confirm"));
    }

}
