package br.com.rocketseat.hiokdev.planner_java.participant;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

    public void registerParticipantsToTrip(List<String> participantsToInvite, UUID tripId) {
    }

    public void triggerConfirmationEmailToParticipants(UUID tripId){
    }

}
