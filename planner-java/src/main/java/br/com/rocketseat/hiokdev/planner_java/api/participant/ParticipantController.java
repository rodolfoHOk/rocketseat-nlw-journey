package br.com.rocketseat.hiokdev.planner_java.api.participant;

import br.com.rocketseat.hiokdev.planner_java.api.participant.dto.ParticipantRequestPayload;
import br.com.rocketseat.hiokdev.planner_java.domain.participant.Participant;
import br.com.rocketseat.hiokdev.planner_java.domain.participant.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantRepository participantRepository;

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Participant> confirmParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestPayload payload){
        Optional<Participant> participant = this.participantRepository.findById(id);
        if(participant.isPresent()){
            Participant rawParticipant = participant.get();
            rawParticipant.setIsConfirmed(true);
            rawParticipant.setName(payload.name());
            rawParticipant = this.participantRepository.save(rawParticipant);
            return ResponseEntity.ok(rawParticipant);
        }
        return ResponseEntity.notFound().build();
    }

}
