import { FormEvent } from 'react';
import { useNavigate } from 'react-router-dom';
import { Calendar, Tag } from 'lucide-react';
import { format } from 'date-fns';
import dayjs from 'dayjs';
import { Modal } from '../../../../components/modal';
import { FormField } from '../../../../components/form-field';
import { Button } from '../../../../components/button';
import { Trip } from '../..';
import { api } from '../../../../lib/axios';
import { validateActivityField } from '../../../../validations/validate-activity-field';

interface CreateActivityModalProps {
  trip: Trip | undefined;
  closeCreateActivityModal: () => void;
  showAlert: (message: string) => void;
}

export function CreateActivityModal({
  trip,
  closeCreateActivityModal,
  showAlert,
}: CreateActivityModalProps) {
  const navigate = useNavigate();

  async function createActivity(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const title = data.get('title')?.toString();
    const occurs_at = data.get('occurs_at')?.toString();
    if (!title) {
      showAlert('Preencha o nome da atividade');
      return;
    }
    if (!occurs_at) {
      showAlert('Preencha a data e hora da atividade');
      return;
    }
    if (!validateActivityField.title(title)) {
      showAlert('Nome da atividade deve ter no mínimo 4 caracteres');
      return;
    }
    if (!validateActivityField.occursAt(occurs_at)) {
      showAlert('Data/Hora informada inválida');
      return;
    }
    if (trip && dayjs(occurs_at).isBefore(trip.starts_at)) {
      showAlert('Data/Hora informada está fora do período da viagem');
      return;
    }
    if (trip && dayjs(occurs_at).isAfter(trip.ends_at)) {
      showAlert('Data/Hora informada está fora do período da viagem');
      return;
    }
    try {
      await api.post(`/trips/${trip?.id}/activities`, {
        title,
        occurs_at,
      });
      navigate(0);
    } catch (error) {
      showAlert(
        'Erro ao tentar cadastrar atividade. Tente novamente mais tarde'
      );
    }
  }

  return (
    <Modal handleClose={closeCreateActivityModal}>
      <Modal.Header>
        <Modal.Title>Cadastrar atividade</Modal.Title>

        <Modal.SubTitle>
          Todos convidados podem visualizar as atividades.
        </Modal.SubTitle>
      </Modal.Header>

      <Modal.Content>
        <form onSubmit={createActivity} className="space-y-3">
          <FormField>
            <Tag className="text-zinc-400 size-5" />

            <FormField.Input name="title" placeholder="Qual a atividade?" />
          </FormField>

          <FormField>
            <Calendar className="text-zinc-400 size-5" />

            {trip && (
              <FormField.Input
                type="datetime-local"
                name="occurs_at"
                placeholder="Data e horário da atividade"
                min={format(trip?.starts_at, "yyyy'-'MM'-'dd'T'hh':'mm")}
                max={format(trip?.ends_at, "yyyy'-'MM'-'dd'T'hh':'mm")}
              />
            )}
          </FormField>

          <Button variant="primary" size="full" type="submit">
            <span>Salvar atividade</span>
          </Button>
        </form>
      </Modal.Content>
    </Modal>
  );
}
