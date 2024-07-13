import { FormEvent } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { Mail, SquareMousePointer, User } from 'lucide-react';
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';
import { Modal } from '../../../../components/modal';
import { FormField } from '../../../../components/form-field';
import { Button } from '../../../../components/button';
import { api } from '../../../../lib/axios';
import { Participant, Trip } from '../..';
import { validateParticipantField } from '../../../../validations/validate-participant-field';

interface ConfirmPresenceModalProps {
  trip: Trip | undefined;
  notConfirmedParticipant: Participant[];
  closeConfirmPresenceModal: () => void;
  showAlert: (message: string) => void;
}

export function ConfirmPresenceModal({
  trip,
  notConfirmedParticipant,
  closeConfirmPresenceModal,
  showAlert,
}: ConfirmPresenceModalProps) {
  const [searchParams, setSearchParams] = useSearchParams();
  const navigate = useNavigate();

  const participantId = searchParams.get('participant');

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
      showAlert('Preencha todos os campos.');
      return;
    }
    if (!validateParticipantField.name(name)) {
      showAlert('Nome deve ter ao menos 3 caracteres');
      return;
    }
    if (!validateParticipantField.email(email)) {
      showAlert('E-mail informado inválido');
      return;
    }
    try {
      await api.patch(`/participants/${id}/confirm`, {
        name,
        email,
      });
      if (participantId) {
        searchParams.delete('participant');
        setSearchParams(searchParams);
      }
      navigate(0);
    } catch (error) {
      showAlert(
        'Erro ao tentar confirmar presença. Tente novamente mais tarde'
      );
    }
  }

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
          {`Você foi convidado(a) para participar de uma viagem para `}
          <span className="font-semibold text-zinc-100">
            {trip?.destination}
          </span>
          {` nas datas de `}
          <span className="font-semibold text-zinc-100">{displayedDate}</span>
          {'.'}
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
                defaultValue=""
              >
                <option value="" disabled>
                  Selecione o participante a confirmar
                </option>
                {notConfirmedParticipant.map((participant) => (
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
            <span>
              {participantId
                ? 'Confirmar minha presença'
                : 'Confirmar presença'}
            </span>
          </Button>
        </form>
      </Modal.Content>
    </Modal>
  );
}
