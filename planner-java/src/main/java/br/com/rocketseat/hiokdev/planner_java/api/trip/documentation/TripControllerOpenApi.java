package br.com.rocketseat.hiokdev.planner_java.api.trip.documentation;

import br.com.rocketseat.hiokdev.planner_java.api.activity.dto.ActivitiesResponse;
import br.com.rocketseat.hiokdev.planner_java.api.activity.dto.ActivityCreateResponse;
import br.com.rocketseat.hiokdev.planner_java.api.activity.dto.ActivityRequestPayload;
import br.com.rocketseat.hiokdev.planner_java.api.common.dto.ProblemResponse;
import br.com.rocketseat.hiokdev.planner_java.api.link.dto.LinkCreateResponse;
import br.com.rocketseat.hiokdev.planner_java.api.link.dto.LinkData;
import br.com.rocketseat.hiokdev.planner_java.api.link.dto.LinkRequestPayload;
import br.com.rocketseat.hiokdev.planner_java.api.participant.dto.InviteParticipantRequestPayload;
import br.com.rocketseat.hiokdev.planner_java.api.participant.dto.ParticipantCreateResponse;
import br.com.rocketseat.hiokdev.planner_java.api.participant.dto.ParticipantData;
import br.com.rocketseat.hiokdev.planner_java.api.trip.dto.TripCreateRequestPayload;
import br.com.rocketseat.hiokdev.planner_java.api.trip.dto.TripCreateResponse;
import br.com.rocketseat.hiokdev.planner_java.api.trip.dto.TripData;
import br.com.rocketseat.hiokdev.planner_java.api.trip.dto.TripUpdateRequestPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Tag(name = "Trip")
public interface TripControllerOpenApi {

    @Operation(
            summary = "Create new trip",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class)))
            }
    )
    @PostMapping
    ResponseEntity<TripCreateResponse> createTrip(
            @RequestBody @Valid TripCreateRequestPayload payload
    );

    @Operation(
            summary = "Get trip by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class)))
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<TripData> getTripDetails(
            @Parameter(description = "Trip ID", example = "1f754eb9-d4a8-4838-b34b-f79c074a00bc") @PathVariable UUID id
    );

    @Operation(
            summary = "Update trip by ID",
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
    @PutMapping("/{id}")
    ResponseEntity<Void> updateTrip(
            @Parameter(description = "Trip ID", example = "1f754eb9-d4a8-4838-b34b-f79c074a00bc") @PathVariable UUID id,
            @RequestBody @Valid TripUpdateRequestPayload payload
    );

    @Operation(
            summary = "Confirm trip by ID and send invitation emails",
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
    @GetMapping("/{id}/confirm")
    ResponseEntity<Void> confirmTrip(
            @Parameter(description = "Trip ID", example = "1f754eb9-d4a8-4838-b34b-f79c074a00bc") @PathVariable UUID id
    );

    @Operation(
            summary = "List trip participants by trip ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class)))
            }
    )
    @GetMapping("/{id}/participants")
    ResponseEntity<List<ParticipantData>> getAllParticipants(
            @Parameter(description = "Trip ID", example = "1f754eb9-d4a8-4838-b34b-f79c074a00bc") @PathVariable UUID id
    );

    @Operation(
            summary = "Invite participant to trip by trip ID and send email if the trip has already been confirmed",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class)))
            }
    )
    @PostMapping("/{id}/invites")
    ResponseEntity<ParticipantCreateResponse> inviteParticipant(
            @Parameter(description = "Trip ID", example = "1f754eb9-d4a8-4838-b34b-f79c074a00bc") @PathVariable UUID id,
            @RequestBody @Valid InviteParticipantRequestPayload payload
    );

    @Operation(
            summary = "Register new trip activity by trip ID",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class)))
            }
    )
    @PostMapping("/{id}/activities")
    ResponseEntity<ActivityCreateResponse> registerActivity(
            @Parameter(description = "Trip ID", example = "1f754eb9-d4a8-4838-b34b-f79c074a00bc") @PathVariable UUID id,
            @RequestBody @Valid ActivityRequestPayload payload
    );

    @Operation(
            summary = "List trip activities separated by day by trip ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class)))
            }
    )
    @GetMapping("/{id}/activities")
    ResponseEntity<ActivitiesResponse> getAllActivities(
            @Parameter(description = "Trip ID", example = "1f754eb9-d4a8-4838-b34b-f79c074a00bc") @PathVariable UUID id
    ) throws JsonProcessingException;

    @Operation(
            summary = "Register new trip link by trip ID",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class)))
            }
    )
    @PostMapping("/{id}/links")
    ResponseEntity<LinkCreateResponse> registerLink(
            @Parameter(description = "Trip ID", example = "1f754eb9-d4a8-4838-b34b-f79c074a00bc") @PathVariable UUID id,
            @RequestBody @Valid LinkRequestPayload payload
    );

    @Operation(
            summary = "List trip links by trip ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ProblemResponse.class)))
            }
    )
    @GetMapping("/{id}/links")
    ResponseEntity<List<LinkData>> getAllLinks(
            @Parameter(description = "Trip ID", example = "1f754eb9-d4a8-4838-b34b-f79c074a00bc") @PathVariable UUID id
    );

}
