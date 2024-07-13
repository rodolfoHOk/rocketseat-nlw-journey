import { FormEvent, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Link2, Tag } from 'lucide-react';
import { Modal } from '../../../../components/modal';
import { FormField } from '../../../../components/form-field';
import { Button } from '../../../../components/button';
import { api } from '../../../../lib/axios';
import { validateLinkField } from '../../../../validations/validate-link-field';

interface CreateLinkModalProps {
  closeCreateLinkModal: () => void;
  showAlert: (message: string) => void;
}

export function CreateLinkModal({
  closeCreateLinkModal,
  showAlert,
}: CreateLinkModalProps) {
  const { tripId } = useParams();
  const navigate = useNavigate();

  const [isCreatingLink, setIsCreatingLink] = useState(false);

  async function createLink(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const title = data.get('title')?.toString();
    const url = data.get('url')?.toString();
    if (!title) {
      showAlert('Preencha o título do link');
      return;
    }
    if (!url) {
      showAlert('Preencha a url do link');
      return;
    }
    if (!validateLinkField.title(title)) {
      showAlert('Título do link deve ter ao menos 4 caracteres');
      return;
    }
    if (!validateLinkField.url(url)) {
      showAlert('URL informada inválida');
      return;
    }
    try {
      setIsCreatingLink(true);
      await api.post(`/trips/${tripId}/links`, {
        title,
        url,
      });
      navigate(0);
    } catch (error) {
      showAlert('Erro ao tentar cadastrar link. Tente novamente mais tarde');
    } finally {
      setIsCreatingLink(false);
    }
  }

  return (
    <Modal handleClose={closeCreateLinkModal}>
      <Modal.Header>
        <Modal.Title>Cadastrar link</Modal.Title>

        <Modal.SubTitle>
          Todos convidados podem visualizar os links.
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

          <Button
            variant="primary"
            size="full"
            type="submit"
            isLoading={isCreatingLink}
          >
            <span>Salvar link</span>
          </Button>
        </form>
      </Modal.Content>
    </Modal>
  );
}
