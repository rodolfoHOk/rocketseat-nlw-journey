import { useState } from 'react';
import { ArrowRight, Calendar, MapPin, Settings2 } from 'lucide-react';
import { DateRange } from 'react-day-picker';
import { FormField } from '../../../../components/form-field';
import { Button } from '../../../../components/button';
import { format } from 'date-fns';
import { RangeDatePickerModal } from '../modals/range-date-picker-modal';

interface DestinationAndDateStepProps {
  isGuestsInputOpen: boolean;
  openGuestsInput: () => void;
  closeGuestsInput: () => void;
}

export function DestinationAndDateStep({
  isGuestsInputOpen,
  openGuestsInput,
  closeGuestsInput,
}: DestinationAndDateStepProps) {
  const [isDatePickerOpen, setIsDatePickerOpen] = useState(false);

  const [tripStartAndEndDates, setTripStartAndEndDates] = useState<
    DateRange | undefined
  >();

  function openDatePicker() {
    setIsDatePickerOpen(true);
  }

  function closeDatePicker() {
    setIsDatePickerOpen(false);
  }

  const displayedDate =
    tripStartAndEndDates && tripStartAndEndDates.from && tripStartAndEndDates.to
      ? format(tripStartAndEndDates.from, "d' de 'LLL")
          .concat(' até ')
          .concat(format(tripStartAndEndDates.to, "d' de 'LLL"))
      : null;

  return (
    <div className="h-16 bg-zinc-900 px-4 rounded-xl flex items-center gap-3 shadow-shape">
      <FormField border="borderless" width="flex">
        <MapPin className="size-5 text-zinc-400" />

        <FormField.Input
          inputWidth="flex"
          type="text"
          placeholder="Para onde você vai?"
          disabled={isGuestsInputOpen}
        />
      </FormField>

      <button
        className="flex items-center gap-2 text-left w-[240px]"
        disabled={isGuestsInputOpen}
        onClick={openDatePicker}
      >
        <Calendar className="size-5 text-zinc-400" />

        <span className="text-lg text-zinc-400 w-40 flex-1">
          {displayedDate || 'Quando?'}
        </span>
      </button>

      {isDatePickerOpen && (
        <RangeDatePickerModal
          tripStartAndEndDates={tripStartAndEndDates}
          closeDatePicker={closeDatePicker}
          setTripStartAndEndDates={setTripStartAndEndDates}
        />
      )}

      <div className="w-px h-6 bg-zinc-800" />

      {isGuestsInputOpen ? (
        <Button variant="secondary" onClick={closeGuestsInput}>
          <span>Alterar local/data</span>

          <Settings2 className="size-5" />
        </Button>
      ) : (
        <Button variant="primary" onClick={openGuestsInput}>
          <span>Continuar</span>

          <ArrowRight className="size-5" />
        </Button>
      )}
    </div>
  );
}
