package br.com.rocketseat.hiokdev.planner_java.domain.trip;

import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.ValidationException;
import br.com.rocketseat.hiokdev.planner_java.domain.common.gateway.MailGateway;
import br.com.rocketseat.hiokdev.planner_java.domain.participant.ParticipantService;
import br.com.rocketseat.hiokdev.planner_java.factory.EmailFactory;
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

import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private TripQueryService tripQueryService;

    @Mock
    private ParticipantService participantService;

    @Mock
    private MailGateway mailGateway;

    @InjectMocks
    private TripService tripService;

    @Test
    void shouldReturnTripWhenCreateWithValidData() {
        var tripToSave = TripFactory.getTripWithoutId();
        when(tripRepository.save(tripToSave)).thenAnswer(invocationOnMock -> {
            var trip = invocationOnMock.getArgument(0, Trip.class);
            return trip.toBuilder().id(UUID.randomUUID()).build();
        });
        doNothing().when(participantService).registerParticipantsToTrip(any(), any());
        doNothing().when(mailGateway).send(any());

        var sutTrip = tripService.create(tripToSave, EmailFactory.getEmailList());

        assertThat(sutTrip.getId()).isNotNull();
        assertThat(sutTrip).usingRecursiveComparison().ignoringFields("id").isEqualTo(tripToSave);
        verify(tripRepository).save(any());
        verify(participantService).registerParticipantsToTrip(any(), any());
        verify(mailGateway).send(any());
    }

    @MethodSource("providesInvalidTrips")
    @ParameterizedTest
    void shouldThrowValidationExceptionWhenCreateWithInValidData(Trip trip) {
        assertThatThrownBy(() -> tripService.create(trip, EmailFactory.getEmailList()))
                .isInstanceOf(ValidationException.class);
        verify(tripRepository, times(0)).save(any());
        verify(participantService, times(0)).registerParticipantsToTrip(any(), any());
        verify(mailGateway, times(0)).send(any());
    }

    @Test
    void shouldReturnTripWhenUpdateWithValidData() {
        var existingTripId = UUID.randomUUID();
        var existingTrip = TripFactory.getTripWithId(existingTripId);
        when(tripQueryService.getById(existingTripId)).thenReturn(existingTrip);
        when(tripRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0, Trip.class));

        var tripToUpdate = TripFactory.getTripToUpdate();
        var sutTrip = tripService.update(existingTripId, tripToUpdate);

        assertThat(sutTrip.getId()).isEqualTo(existingTripId);
        assertThat(sutTrip.getDestination()).isEqualTo(tripToUpdate.getDestination());
        assertThat(sutTrip.getStartsAt()).isEqualTo(tripToUpdate.getStartsAt());
        assertThat(sutTrip.getEndsAt()).isEqualTo(tripToUpdate.getEndsAt());
        assertThat(sutTrip.getIsConfirmed()).isEqualTo(existingTrip.getIsConfirmed());
        assertThat(sutTrip.getOwnerName()).isEqualTo(existingTrip.getOwnerName());
        assertThat(sutTrip.getOwnerEmail()).isEqualTo(existingTrip.getOwnerEmail());
        verify(tripQueryService).getById(any());
        verify(tripRepository).save(any());
    }

    @MethodSource("providesIdsAndInvalidTrips")
    @ParameterizedTest
    void shouldThrowValidationExceptionWhenUpdateWithInValidData(UUID id, Trip trip) {
        assertThatThrownBy(() -> tripService.update(id, trip)).isInstanceOf(ValidationException.class);
        verify(tripRepository, times(0)).save(any());
    }

    @Test
    void shouldReturnTripWhenConfirm() {
        var existingTripId = UUID.randomUUID();
        var existingTrip = TripFactory.getTripWithId(existingTripId);
        assertThat(existingTrip.getIsConfirmed()).isFalse();
        when(tripQueryService.getById(existingTripId)).thenReturn(existingTrip);
        when(tripRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0, Trip.class));

        var sutTrip = tripService.confirm(existingTripId);

        assertThat(sutTrip).usingRecursiveComparison().ignoringFields("isConfirmed").isEqualTo(existingTrip);
        assertThat(sutTrip.getIsConfirmed()).isTrue();
        verify(tripQueryService).getById(any());
        verify(tripRepository).save(any());
    }

    private static Stream<Arguments> providesInvalidTrips() {
        return Stream.of(
                Arguments.of(TripFactory.getTripWithInvalidStartsAt()),
                Arguments.of(TripFactory.getTripWithInvalidEndsAt()),
                Arguments.of(TripFactory.getTripWithEndsAtBeforeStartsAt())
        );
    }

    private static Stream<Arguments> providesIdsAndInvalidTrips() {
        var tripId = UUID.randomUUID();

        return Stream.of(
                Arguments.of(tripId, TripFactory.getTripWithInvalidStartsAt().toBuilder().id(tripId).build()),
                Arguments.of(tripId, TripFactory.getTripWithInvalidEndsAt().toBuilder().id(tripId).build()),
                Arguments.of(tripId, TripFactory.getTripWithEndsAtBeforeStartsAt().toBuilder().id(tripId).build())
        );
    }

}
