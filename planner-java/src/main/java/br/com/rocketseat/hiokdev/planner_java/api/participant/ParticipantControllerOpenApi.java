package br.com.rocketseat.hiokdev.planner_java.api.participant;

import br.com.rocketseat.hiokdev.planner_java.api.common.dto.ProblemResponse;
import br.com.rocketseat.hiokdev.planner_java.api.participant.dto.ParticipantRequestPayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Tag(name = "Participant")
public interface ParticipantControllerOpenApi {

    @Operation(
            summary = "Confirms participant presence by participant ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No content"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class)))
            }
    )
    @PostMapping("/{id}/confirm")
    ResponseEntity<Void> confirmParticipant(
            @Parameter(description = "Participant ID", example = "b1fe1848-b93d-425e-8dc0-d91b4b68f34c") @PathVariable UUID id,
            @RequestBody @Valid ParticipantRequestPayload payload
    );

}
