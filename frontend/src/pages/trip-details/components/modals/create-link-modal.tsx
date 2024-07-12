import { FormEvent } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Link2, Tag } from 'lucide-react';
import { Modal } from '../../../../components/modal';
import { FormField } from '../../../../components/form-field';
import { Button } from '../../../../components/button';
import { api } from '../../../../lib/axios';

interface CreateLinkModalProps {
  closeCreateLinkModal: () => void;
}

export function CreateLinkModal({
  closeCreateLinkModal,
}: CreateLinkModalProps) {
  const { tripId } = useParams();
  const navigate = useNavigate();

  async function createLink(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const title = data.get('title')?.toString();
    const url = data.get('url')?.toString();
    await api.post(`/trips/${tripId}/links`, {
      title,
      url,
    });
    navigate(0);
  }

  return (
    <Modal handleClose={closeCreateLinkModal}>
      <Modal.Header>
        <Modal.Title>Cadastrar atividade</Modal.Title>

        <Modal.SubTitle>
          Todos convidados podem visualizar as atividades.
        </Modal.SubTitle>
      </Modal.Header>

      <Modal.Content>
        <form onSubmit={createLink} className="space-y-3">
          <FormField>
            <Tag className="text-zinc-400 size-5" />

            <FormField.Input name="title" placeholder="Título do link" />
          </FormField>

          <FormField>
            <Link2 className="text-zinc-400 size-5" />

            <FormField.Input name="url" placeholder="URL" />
          </FormField>

          <Button variant="primary" size="full" type="submit">
            <span>Salvar link</span>
          </Button>
        </form>
      </Modal.Content>
    </Modal>
  );
}
