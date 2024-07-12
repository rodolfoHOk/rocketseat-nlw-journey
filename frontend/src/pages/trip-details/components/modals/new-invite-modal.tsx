import { useNavigate, useParams } from 'react-router-dom';
import { api } from '../../../../lib/axios';
import { FormEvent } from 'react';
import { Modal } from '../../../../components/modal';
import { FormField } from '../../../../components/form-field';
import { Mail } from 'lucide-react';
import { Button } from '../../../../components/button';

interface NewInviteModalProps {
  closeNewInviteModal: () => void;
}

export function NewInviteModal({ closeNewInviteModal }: NewInviteModalProps) {
  const { tripId } = useParams();
  const navigate = useNavigate();

  async function newInvite(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const email = data.get('email')?.toString();
    await api.post(`/trips/${tripId}/invites`, {
      email,
    });
    navigate(0);
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
        <form onSubmit={newInvite} className="space-y-3">
          <FormField>
            <Mail className="text-zinc-400 size-5" />

            <FormField.Input type="email" name="email" placeholder="E-mail" />
          </FormField>

          <Button variant="primary" size="full" type="submit">
            <span>Enviar convite</span>
          </Button>
        </form>
      </Modal.Content>
    </Modal>
  );
}
