package br.com.rocketseat.hiokdev.planner_java.api.participant;

import br.com.rocketseat.hiokdev.planner_java.api.participant.dto.ParticipantRequestPayload;
import br.com.rocketseat.hiokdev.planner_java.domain.participant.Participant;
import br.com.rocketseat.hiokdev.planner_java.domain.participant.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Participant> confirmParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestPayload payload){
        var participant = this.participantService.confirmParticipant(id, ParticipantRequestPayload.toDomain(payload));
        return ResponseEntity.noContent().build();
    }

}
