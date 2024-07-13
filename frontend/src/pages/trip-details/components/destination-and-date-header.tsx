import { Calendar, MapPin, Settings2 } from 'lucide-react';
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';
import { Button } from '../../../components/button';
import { Loading } from '../../../components/loading';
import { Trip } from '..';

interface DestinationAndDateHeaderProps {
  isLoadingTrip: boolean;
  trip: Trip | undefined;
  openUpdateTripModal: () => void;
}

export function DestinationAndDateHeader({
  isLoadingTrip,
  trip,
  openUpdateTripModal,
}: DestinationAndDateHeaderProps) {
  const displayedDate = trip
    ? format(trip.starts_at, "d' de 'LLL", { locale: ptBR })
        .concat(' até ')
        .concat(format(trip.ends_at, "d' de 'LLL", { locale: ptBR }))
    : null;

  return (
    <div className="px-4 h-16 rounded-xl bg-zinc-900 shadow-shape flex items-center justify-between">
      <div className="flex items-center gap-2">
        <MapPin className="size-5 text-zinc-400" />

        <span className="text-zinc-100">
          {isLoadingTrip ? <Loading color="secondary" /> : trip?.destination}
        </span>
      </div>

      <div className="flex items-center gap-5">
        <div className="flex items-center gap-2">
          <Calendar className="size-5 text-zinc-400" />

          <span className="text-zinc-100">
            {isLoadingTrip ? <Loading color="secondary" /> : displayedDate}
          </span>
        </div>

        <div className="w-px h-6 bg-zinc-800" />

        <Button variant="secondary" onClick={openUpdateTripModal}>
          <span>Alterar local/data</span>

          <Settings2 className="size-5" />
        </Button>
      </div>
    </div>
  );
}
