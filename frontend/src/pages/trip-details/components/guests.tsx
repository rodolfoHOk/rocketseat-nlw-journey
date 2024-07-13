import { CheckCircle2, CircleDashed, UserCog } from 'lucide-react';
import { Button } from '../../../components/button';
import { Loading } from '../../../components/loading';
import { Participant } from '..';

interface GuestsProps {
  isLoadingParticipants: boolean;
  participants: Participant[];
  openManagerGuestsModal: () => void;
}

export function Guests({
  isLoadingParticipants,
  participants,
  openManagerGuestsModal,
}: GuestsProps) {
  return (
    <div className="space-y-6">
      <h2 className="font-semibold text-xl">Convidados</h2>

      {isLoadingParticipants && <Loading color="secondary" size="lg" />}

      <div className="space-y-5">
        {participants.map((participant, index) => (
          <div
            key={participant.id}
            className="flex items-center justify-between gap-4"
          >
            <div className="space-y-1.5">
              <span className="block font-medium text-zinc-100">
                {participant.name ?? `Convidado ${index}`}
              </span>

              <span className="block text-sm text-zinc-400 truncate">
                {participant.email}
              </span>
            </div>

            {participant.is_confirmed ? (
              <CheckCircle2 className="text-green-400 size-5 shrink-0" />
            ) : (
              <CircleDashed className="text-zinc-400 size-5 shrink-0" />
            )}
          </div>
        ))}
      </div>

      <Button onClick={openManagerGuestsModal} variant="secondary" size="full">
        <UserCog className="size-5" />

        <span>Gerenciar convidados</span>
      </Button>
    </div>
  );
}
