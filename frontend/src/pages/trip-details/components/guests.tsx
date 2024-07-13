import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { CheckCircle2, CircleDashed, UserCog } from 'lucide-react';
import { Button } from '../../../components/button';
import { Loading } from '../../../components/loading';
import { api } from '../../../lib/axios';

export interface Participant {
  id: string;
  name: string | null;
  email: string;
  is_confirmed: boolean;
}

interface GuestsProps {
  openManagerGuestsModal: () => void;
  showAlert: (message: string) => void;
}

export function Guests({ openManagerGuestsModal, showAlert }: GuestsProps) {
  const { tripId } = useParams();
  const [participants, setParticipants] = useState<Participant[]>([]);

  const [isLoadingData, setIsLoadingData] = useState(true);

  async function fetchData() {
    try {
      const response = await api.get(`trips/${tripId}/participants`);
      setParticipants(response.data.participants);
      setIsLoadingData(false);
    } catch (error) {
      showAlert('Erro ao tentar buscar dados de links da viagem');
      setIsLoadingData(false);
    }
  }

  useEffect(() => {
    fetchData();
  }, [tripId]);

  return (
    <div className="space-y-6">
      <h2 className="font-semibold text-xl">Convidados</h2>

      {isLoadingData && <Loading color="secondary" size="lg" />}

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
