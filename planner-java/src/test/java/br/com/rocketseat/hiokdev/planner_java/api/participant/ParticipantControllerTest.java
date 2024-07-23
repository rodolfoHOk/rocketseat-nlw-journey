package br.com.rocketseat.hiokdev.planner_java.api.participant;

import br.com.rocketseat.hiokdev.planner_java.api.participant.dto.ParticipantRequestPayload;
import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.NotFoundException;
import br.com.rocketseat.hiokdev.planner_java.domain.participant.ParticipantService;
import br.com.rocketseat.hiokdev.planner_java.factory.domain.ParticipantFactory;
import br.com.rocketseat.hiokdev.planner_java.factory.dto.ParticipantRequestPayloadFactory;
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
@WebMvcTest(ParticipantController.class)
public class ParticipantControllerTest {

    @MockBean
    private ParticipantService participantService;

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
    void shouldReturnNoContentWhenConfirmParticipant() throws Exception {
        var participantId = UUID.randomUUID();
        var participant = ParticipantFactory.getParticipantWithId(participantId);
        when(participantService.confirmParticipant(any(), any())).thenReturn(participant);

        var payload = ParticipantRequestPayloadFactory.getPayload();
        mockMvc.perform(post("/participants/" + participantId + "/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody(payload)))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNoContentWhenConfirmParticipantWithOnlyNamePayload() throws Exception {
        var participantId = UUID.randomUUID();
        var participant = ParticipantFactory.getParticipantWithId(participantId);
        when(participantService.confirmParticipant(any(), any())).thenReturn(participant);

        var payload = ParticipantRequestPayloadFactory.getPayloadWithOnlyName();
        mockMvc.perform(post("/participants/" + participantId + "/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody(payload)))
                .andExpect(status().isNoContent());
    }

    private static Stream<Arguments> providesParticipantRequestPayloads() {
        return Stream.of(
                Arguments.of(ParticipantRequestPayloadFactory.getPayloadWithBlankName(), "name"),
                Arguments.of(ParticipantRequestPayloadFactory.getPayloadWithShortName(), "name"),
                Arguments.of(ParticipantRequestPayloadFactory.getPayloadWithLongName(), "name"),
                Arguments.of(ParticipantRequestPayloadFactory.getPayloadWithInvalidEmail(), "email")
        );
    }

    @MethodSource("providesParticipantRequestPayloads")
    @ParameterizedTest
    void shouldReturnBadRequestWhenConfirmParticipantWithInvalidPayload(ParticipantRequestPayload payload, String fieldName) throws Exception {
        var participantId = UUID.randomUUID();

        mockMvc.perform(post("/participants/" + participantId + "/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody(payload)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value("/participants/" + participantId + "/confirm"))
                .andExpect(jsonPath("$.fields[0].name").value(fieldName));
    }

    @Test
    void shouldReturnBadRequestWhenConfirmParticipantWithInvalidUuid() throws Exception {
        var participantId = "invalid-uuid";

        var payload = ParticipantRequestPayloadFactory.getPayload();
        mockMvc.perform(post("/participants/" + participantId + "/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody(payload)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value("/participants/" + participantId + "/confirm"));
    }

    @Test
    void shouldReturnNotFoundWhenConfirmParticipantWithNonExistingId() throws Exception {
        var nonExistingParticipantId = UUID.randomUUID();
        doThrow(NotFoundException.class).when(participantService).confirmParticipant(any(), any());

        var payload = ParticipantRequestPayloadFactory.getPayload();
        mockMvc.perform(post("/participants/" + nonExistingParticipantId + "/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody(payload)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.path").value("/participants/" + nonExistingParticipantId + "/confirm"));
    }

}
