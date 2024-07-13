import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Calendar, MapPin } from 'lucide-react';
import { DateRange } from 'react-day-picker';
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';
import { Modal } from '../../../../components/modal';
import { FormField } from '../../../../components/form-field';
import { RangeDatePickerModal } from '../../../../components/range-date-picker-modal';
import { Button } from '../../../../components/button';
import { Trip } from '../..';
import { api } from '../../../../lib/axios';
import { validateTripField } from '../../../../validations/validate-trip-field';

interface UpdateTripModalProps {
  trip: Trip | undefined;
  closeUpdateTripModal: () => void;
  showAlert: (message: string) => void;
}

export function UpdateTripModal({
  trip,
  closeUpdateTripModal,
  showAlert,
}: UpdateTripModalProps) {
  const navigate = useNavigate();

  const [isDatePickerOpen, setIsDatePickerOpen] = useState(false);
  const [isUpdatingTrip, setIsUpdatingTrip] = useState(false);

  const [destination, setDestination] = useState('');
  const [tripStartAndEndDates, setTripStartAndEndDates] = useState<
    DateRange | undefined
  >();

  function openDatePicker() {
    setIsDatePickerOpen(true);
  }

  function closeDatePicker() {
    setIsDatePickerOpen(false);
  }

  async function updateTrip() {
    if (!destination.trim()) {
      showAlert('Preencha o destino para continuar');
      return;
    }
    if (!validateTripField.destination(destination)) {
      showAlert('Destino deve ter ao menos 4 caracteres');
      return;
    }
    if (
      !tripStartAndEndDates ||
      !tripStartAndEndDates.from ||
      !tripStartAndEndDates.to
    ) {
      showAlert('Selecione o período da viagem para continuar');
      return;
    }
    if (!validateTripField.startsAt(tripStartAndEndDates?.from?.toString())) {
      showAlert('Data inicial da viagem inválida');
      return;
    }
    if (!validateTripField.startsAt(tripStartAndEndDates?.to?.toString())) {
      showAlert('Data final da viagem inválida');
      return;
    }
    try {
      setIsUpdatingTrip(true);
      await api.put(`/trips/${trip?.id}`, {
        destination,
        starts_at: tripStartAndEndDates.from,
        ends_at: tripStartAndEndDates.to,
      });
      navigate(0);
    } catch (error) {
      showAlert('Erro ao tentar atualizar viagem. Tente novamente mais tarde');
    } finally {
      setIsUpdatingTrip(false);
    }
  }

  useEffect(() => {
    setDestination(trip?.destination || '');
    const dateRange: DateRange = {
      from: new Date(trip?.starts_at || ''),
      to: new Date(trip?.ends_at || ''),
    };
    setTripStartAndEndDates(dateRange);
  }, [trip]);

  const displayedDate =
    tripStartAndEndDates && tripStartAndEndDates.from && tripStartAndEndDates.to
      ? format(tripStartAndEndDates.from, "d' de 'LLL", { locale: ptBR })
          .concat(' até ')
          .concat(
            format(tripStartAndEndDates.to, "d' de 'LLL", { locale: ptBR })
          )
      : null;

  return (
    <Modal handleClose={closeUpdateTripModal}>
      <Modal.Header>
        <Modal.Title>Atualizar Viagem</Modal.Title>

        <Modal.SubTitle>Alterar local e datas da viagem.</Modal.SubTitle>
      </Modal.Header>

      <Modal.Content>
        <div className="space-y-3">
          <FormField>
            <MapPin className="size-5 text-zinc-400" />

            <FormField.Input
              type="text"
              placeholder="Para onde você vai?"
              value={destination}
              onChange={(event) => setDestination(event.target.value)}
            />
          </FormField>

          <FormField>
            <button
              className="w-full flex items-center gap-2 text-left hover:opacity-80 transition-opacity duration-200"
              onClick={openDatePicker}
            >
              <Calendar className="size-5 text-zinc-400" />

              <span className="text-lg text-zinc-400 w-40 flex-1">
                {displayedDate || 'Quando?'}
              </span>
            </button>
          </FormField>

          {isDatePickerOpen && (
            <RangeDatePickerModal
              tripStartAndEndDates={tripStartAndEndDates}
              closeDatePicker={closeDatePicker}
              setTripStartAndEndDates={setTripStartAndEndDates}
            />
          )}

          <Button
            type="button"
            variant="primary"
            size="full"
            onClick={updateTrip}
            isLoading={isUpdatingTrip}
          >
            <span>Atualizar local/data</span>
          </Button>
        </div>
      </Modal.Content>
    </Modal>
  );
}
