import { FormEvent, useEffect, useState } from 'react';
import { useNavigate, useParams, useSearchParams } from 'react-router-dom';
import { Mail, SquareMousePointer, User } from 'lucide-react';
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';
import { Modal } from '../../../../components/modal';
import { FormField } from '../../../../components/form-field';
import { Button } from '../../../../components/button';
import { Trip } from '../destination-and-date-header';
import { api } from '../../../../lib/axios';
import { Participant } from '../guests';

interface ConfirmPresenceModalProps {
  closeConfirmPresenceModal: () => void;
}

export function ConfirmPresenceModal({
  closeConfirmPresenceModal,
}: ConfirmPresenceModalProps) {
  const { tripId } = useParams();
  const [searchParams, setSearchParams] = useSearchParams();
  const navigate = useNavigate();

  const [trip, setTrip] = useState<Trip | undefined>();
  const [participants, setParticipants] = useState<Participant[]>([]);

  const participantId = searchParams.get('participantId');

  async function confirmPresence(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const name = data.get('name')?.toString();
    const email = data.get('email')?.toString();
    let id: string | null | undefined = participantId;
    if (!id) {
      id = data.get('selectedId')?.toString();
    }
    if (!id || !name?.trim() || !email?.trim()) {
      console.log('erro');
      return;
    }
    await api.patch(`/participants/${id}/confirm`, {
      name,
      email,
    });
    if (participantId) {
      searchParams.delete('participantId');
      setSearchParams(searchParams);
    }
    navigate(0);
  }

  useEffect(() => {
    api.get(`trips/${tripId}`).then((response) => setTrip(response.data.trip));
  }, [tripId]);

  useEffect(() => {
    if (!participantId) {
      api.get(`trips/${tripId}/participants`).then((response) => {
        let participants = response.data.participants as Participant[];
        participants = participants.filter(
          (participant) => participant.is_confirmed === false
        );
        setParticipants(participants);
      });
    }
  }, [tripId]);

  const displayedDate = trip
    ? format(trip.starts_at, "d' de 'LLLL", { locale: ptBR })
        .concat(' a ')
        .concat(format(trip.ends_at, "d' de 'LLLL' de 'yyyy", { locale: ptBR }))
    : null;

  return (
    <Modal handleClose={closeConfirmPresenceModal}>
      <Modal.Header>
        <Modal.Title>Confirmar participação</Modal.Title>

        <Modal.SubTitle>
          {`Você foi convidado(a) para participar de uma viagem para `}`
          <span className="font-semibold text-zinc-100">
            {trip?.destination}
          </span>
          {` nas datas de `}
          <span className="font-semibold text-zinc-100">{displayedDate}</span>.
          <br />
          <br />
          {`Para confirmar sua presença na viagem, preencha os dados abaixo:`}
        </Modal.SubTitle>
      </Modal.Header>

      <Modal.Content>
        <form onSubmit={confirmPresence} className="space-y-3">
          {!participantId && (
            <FormField>
              <SquareMousePointer className="size-5" />

              <select
                name="selectedId"
                className="bg-transparent text-lg outline-none"
              >
                <option value="" disabled selected>
                  Selecione o participante a confirmar
                </option>
                {participants.map((participant) => (
                  <option key={participant.id} value={participant.id}>
                    {participant.email}
                  </option>
                ))}
              </select>
            </FormField>
          )}

          <FormField>
            <User className="text-zinc-400 size-5" />

            <FormField.Input
              name="name"
              placeholder={
                participantId ? 'Seu nome completo' : 'Nome completo'
              }
            />
          </FormField>

          <FormField>
            <Mail className="text-zinc-400 size-5" />

            <FormField.Input
              type="email"
              name="email"
              placeholder={participantId ? 'Seu e-mail' : 'E-mail'}
            />
          </FormField>

          <Button variant="primary" size="full" type="submit">
            <span>Salvar link</span>
          </Button>
        </form>
      </Modal.Content>
    </Modal>
  );
}
