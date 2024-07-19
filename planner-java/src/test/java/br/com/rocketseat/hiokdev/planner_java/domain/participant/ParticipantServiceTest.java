package br.com.rocketseat.hiokdev.planner_java.domain.participant;

import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.NotFoundException;
import br.com.rocketseat.hiokdev.planner_java.domain.common.gateway.MailGateway;
import br.com.rocketseat.hiokdev.planner_java.domain.trip.TripQueryService;
import br.com.rocketseat.hiokdev.planner_java.factory.EmailFactory;
import br.com.rocketseat.hiokdev.planner_java.factory.ParticipantFactory;
import br.com.rocketseat.hiokdev.planner_java.factory.TripFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ParticipantServiceTest {

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private TripQueryService tripQueryService;

    @Mock
    private MailGateway mailGateway;

    @InjectMocks
    private ParticipantService participantService;

    @Test
    void shouldRegisterParticipantsToTrip() {
        var emailsToInvite = EmailFactory.getEmailList();
        var trip = TripFactory.getTripWithId(UUID.randomUUID());
        var participantsFromInvites = ParticipantFactory.getParticipantsFromInvites(emailsToInvite, trip);
        when(participantRepository.saveAll(anyList())).thenReturn(participantsFromInvites);
        var ownerParticipant = ParticipantFactory.getOwnerParticipant(trip);
        when(participantRepository.save(any())).thenReturn(ownerParticipant);

        participantService.registerParticipantsToTrip(emailsToInvite, trip);

        verify(participantRepository, times(1)).saveAll(any());
        verify(participantRepository, times(1)).save(any());
    }

    @Test
    void shouldGetAllParticipantsByTripId() {
        var emailsToInvite = EmailFactory.getEmailList();
        var tripId = UUID.randomUUID();
        var trip = TripFactory.getTripWithId(UUID.randomUUID());
        var participantsFromInvites = ParticipantFactory.getParticipantsFromInvites(emailsToInvite, trip);
        when(participantRepository.findByTripId(tripId)).thenReturn(participantsFromInvites);

        var sutParticipants = participantService.getAllParticipantsByTripId(tripId);

        assertThat(sutParticipants.size()).isEqualTo(participantsFromInvites.size());
        assertThat(sutParticipants).usingRecursiveComparison().isEqualTo(participantsFromInvites);
        verify(participantRepository).findByTripId(any());
    }

    @Test
    void shouldReturnParticipantWhenRegisterParticipantToTrip() {
        var tripId = UUID.randomUUID();
        var trip = TripFactory.getTripWithId(tripId);
        when(tripQueryService.getById(any())).thenReturn(trip);
        when(participantRepository.save(any())).thenAnswer(invocationOnMock -> {
            var participant = invocationOnMock.getArgument(0, Participant.class);
            participant.setId(UUID.randomUUID());
            return participant;
        });

        var email = EmailFactory.getEmail();
        var sutParticipant = participantService.registerParticipantToTrip(email, tripId);

        assertThat(sutParticipant.getId()).isNotNull();
        assertThat(sutParticipant.getName()).isEqualTo("");
        assertThat(sutParticipant.getEmail()).isEqualTo(email);
        assertThat(sutParticipant.getIsConfirmed()).isFalse();
        assertThat(sutParticipant.getTrip()).isEqualTo(trip);
        verify(tripQueryService).getById(any());
        verify(participantRepository).save(any());
        verifyNoMoreInteractions(mailGateway);
    }

    @Test
    void shouldReturnParticipantAndSendEmailWhenRegisterParticipantToConfirmedTrip() {
        var tripId = UUID.randomUUID();
        var trip = TripFactory.getTripWithId(tripId).toBuilder().isConfirmed(true).build();
        when(tripQueryService.getById(any())).thenReturn(trip);
        when(participantRepository.save(any())).thenAnswer(invocationOnMock -> {
            var participant = invocationOnMock.getArgument(0, Participant.class);
            participant.setId(UUID.randomUUID());
            return participant;
        });
        doNothing().when(mailGateway).send(any());

        var email = EmailFactory.getEmail();
        var sutParticipant = participantService.registerParticipantToTrip(email, tripId);

        assertThat(sutParticipant.getId()).isNotNull();
        assertThat(sutParticipant.getName()).isEqualTo("");
        assertThat(sutParticipant.getEmail()).isEqualTo(email);
        assertThat(sutParticipant.getIsConfirmed()).isFalse();
        assertThat(sutParticipant.getTrip()).isEqualTo(trip);
        verify(tripQueryService).getById(any());
        verify(participantRepository).save(any());
        verify(mailGateway).send(any());
    }

    @MethodSource("providesParticipantToConfirm")
    @ParameterizedTest
    void shouldReturnParticipantWhenConfirmParticipantWithValidId(Participant participantToConfirm) {
        var existingId = UUID.randomUUID();
        var existingParticipant = ParticipantFactory.getParticipantWithId(existingId);
        assertThat(existingParticipant.getIsConfirmed()).isFalse();
        when(participantRepository.findById(existingId)).thenReturn(Optional.of(existingParticipant));
        when(participantRepository.save(any()))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0, Participant.class));

        var sutParticipant = participantService.confirmParticipant(existingId, participantToConfirm);

        assertThat(sutParticipant.getId()).isEqualTo(existingId);
        assertThat(sutParticipant.getName()).isEqualTo(participantToConfirm.getName());
        assertThat(sutParticipant.getEmail()).isEqualTo(participantToConfirm
                        .getEmail() != null ? participantToConfirm.getEmail() : existingParticipant.getEmail());
        assertThat(sutParticipant.getIsConfirmed()).isTrue();
        assertThat(sutParticipant.getTrip()).isEqualTo(existingParticipant.getTrip());
        verify(participantRepository).findById(any());
        verify(participantRepository).save(any());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenConfirmParticipantWithInvalidId() {
        var nonExistingId = UUID.randomUUID();
        when(participantRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        var participantToConfirm = ParticipantFactory.getParticipantToConfirm();
        assertThatThrownBy(() -> participantService.confirmParticipant(nonExistingId, participantToConfirm))
                .isInstanceOf(NotFoundException.class);

        verify(participantRepository).findById(any());
        verify(participantRepository, times(0)).save(any());
    }

    @Test
    void shouldTriggerConfirmationEmailToParticipants() {
        var emailsToInvite = EmailFactory.getEmailList();
        var tripId = UUID.randomUUID();
        var trip = TripFactory.getTripWithId(tripId);
        var participantsFromInvites = ParticipantFactory.getParticipantsFromInvites(emailsToInvite, trip);
        var participantsFromInvitesAndOwnerParticipant = new ArrayList<Participant>(participantsFromInvites);
        participantsFromInvitesAndOwnerParticipant.add(ParticipantFactory.getOwnerParticipant(trip));
        when(participantRepository.findByTripId(tripId)).thenReturn(participantsFromInvitesAndOwnerParticipant);
        doNothing().when(mailGateway).send(any());

        participantService.triggerConfirmationEmailToParticipants(trip);

        verify(participantRepository).findByTripId(any());
        verify(mailGateway, times(3)).send(any());
    }

    private static Stream<Arguments> providesParticipantToConfirm() {
        var participantToConfirm = ParticipantFactory.getParticipantToConfirm();
        return Stream.of(
                Arguments.of(participantToConfirm),
                Arguments.of(participantToConfirm.toBuilder().email(null).build())
        );
    }

}
