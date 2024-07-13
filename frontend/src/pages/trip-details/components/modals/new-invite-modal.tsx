import { FormEvent, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Mail } from 'lucide-react';
import { Modal } from '../../../../components/modal';
import { FormField } from '../../../../components/form-field';
import { Button } from '../../../../components/button';
import { Participant } from '../..';
import { api } from '../../../../lib/axios';
import { validateInviteField } from '../../../../validations/validate-invite-field';

interface NewInviteModalProps {
  participants: Participant[];
  closeNewInviteModal: () => void;
  showAlert: (message: string) => void;
}

export function NewInviteModal({
  participants,
  closeNewInviteModal,
  showAlert,
}: NewInviteModalProps) {
  const { tripId } = useParams();
  const navigate = useNavigate();

  const [isSendingInvite, setIsSendingInvite] = useState(false);

  async function sendInvite(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const email = data.get('email')?.toString();
    if (!email) {
      showAlert('Preencha o e-mail');
      return;
    }
    if (!validateInviteField.email(email)) {
      showAlert('E-mail informado inválido');
      return;
    }
    const emails = participants.map((participant) => participant.email);
    if (emails.includes(email)) {
      showAlert('E-mail informado já foi convidado');
      return;
    }
    try {
      setIsSendingInvite(true);
      await api.post(`/trips/${tripId}/invites`, {
        email,
      });
      navigate(0);
    } catch (error) {
      showAlert('Erro ao tentar enviar convite. Tente novamente mais tarde');
    } finally {
      setIsSendingInvite(false);
    }
  }

  return (
    <Modal handleClose={closeNewInviteModal}>
      <Modal.Header>
        <Modal.Title>Enviar novo convite</Modal.Title>

        <Modal.SubTitle>
          Envia um e-mail convidando-a para a viagem.
        </Modal.SubTitle>
      </Modal.Header>

      <Modal.Content>
        <form onSubmit={sendInvite} className="space-y-3">
          <FormField>
            <Mail className="text-zinc-400 size-5" />

            <FormField.Input type="email" name="email" placeholder="E-mail" />
          </FormField>

          <Button
            variant="primary"
            size="full"
            type="submit"
            isLoading={isSendingInvite}
          >
            <span>Enviar convite</span>
          </Button>
        </form>
      </Modal.Content>
    </Modal>
  );
}
