package br.com.rocketseat.hiokdev.planner_java.domain.trip;

import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.NotFoundException;
import br.com.rocketseat.hiokdev.planner_java.factory.TripFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class TripQueryServiceTest {

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private TripQueryService tripQueryService;

    @Test
    void shouldReturnTripWhenCallWithExistingId() {
        var existingId = UUID.randomUUID();
        var expectTrip = TripFactory.getTripWithId(existingId);
        when(tripRepository.findById(any())).thenReturn(Optional.of(expectTrip));

        var resultTrip = tripQueryService.getById(existingId);

        assertThat(resultTrip).isEqualTo(expectTrip);
        verify(tripRepository).findById(any());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenCallWithNonExistingId() {
        when(tripRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tripQueryService.getById(UUID.randomUUID())).isInstanceOf(NotFoundException.class);
        verify(tripRepository).findById(any());
    }

}
