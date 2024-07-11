import { ArrowRight, Calendar, MapPin, Settings2 } from 'lucide-react';
import { Button } from '../../../../components/button';

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
  return (
    <div className="h-16 bg-zinc-900 px-4 rounded-xl flex items-center gap-3 shadow-shape">
      <div className="flex items-center gap-2 flex-1 hover:opacity-80 transition-opacity duration-200">
        <MapPin className="size-5 text-zinc-400" />

        <input
          type="text"
          placeholder="Para onde você vai?"
          className="bg-transparent text-lg placeholder-zinc-400 outline-none flex-1"
          disabled={isGuestsInputOpen}
        />
      </div>

      <div className="flex items-center gap-2 hover:opacity-80 transition-opacity duration-200">
        <Calendar className="size-5 text-zinc-400" />

        <input
          type="text"
          placeholder="Quando?"
          className="w-40 bg-transparent text-lg placeholder-zinc-400 outline-none"
          disabled={isGuestsInputOpen}
        />
      </div>

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
