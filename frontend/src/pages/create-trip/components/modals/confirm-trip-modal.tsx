import { FormEvent } from 'react';
import { Mail, User } from 'lucide-react';
import { DateRange } from 'react-day-picker';
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';
import { Modal } from '../../../../components/modal';
import { FormField } from '../../../../components/form-field';
import { Button } from '../../../../components/button';

interface ConfirmTripModalProps {
  destination: string;
  tripStartAndEndDates: DateRange | undefined;
  ownerName: string;
  ownerEmail: string;
  closeConfirmTripModal: () => void;
  createTrip: (event: FormEvent<HTMLFormElement>) => void;
  setOwnerName: (ownerName: string) => void;
  setOwnerEmail: (ownerEmail: string) => void;
}

export function ConfirmTripModal({
  destination,
  tripStartAndEndDates,
  ownerName,
  ownerEmail,
  closeConfirmTripModal,
  createTrip,
  setOwnerName,
  setOwnerEmail,
}: ConfirmTripModalProps) {
  const displayedDate =
    tripStartAndEndDates && tripStartAndEndDates.from && tripStartAndEndDates.to
      ? format(tripStartAndEndDates.from, "d' de 'LLLL", { locale: ptBR })
          .concat(' a ')
          .concat(
            format(tripStartAndEndDates.to, "d' de 'LLLL ' de 'yyyy", {
              locale: ptBR,
            })
          )
      : null;

  return (
    <Modal handleClose={closeConfirmTripModal}>
      <Modal.Header>
        <Modal.Title>Confirmar criação de viagem</Modal.Title>

        <Modal.SubTitle>
          {`Para concluir a criação da viagem para `}
          <span className="font-semibold text-zinc-100">{destination}</span>
          {` nas datas de `}
          <span className="font-semibold text-zinc-100">{displayedDate}</span>
          {` preencha seus dados abaixo:`}
        </Modal.SubTitle>
      </Modal.Header>

      <Modal.Content>
        <form onSubmit={createTrip} className="space-y-3">
          <FormField>
            <User className="text-zinc-400 size-5" />

            <FormField.Input
              type="text"
              name="name"
              placeholder="Seu nome completo"
              value={ownerName}
              onChange={(event) => setOwnerName(event.target.value)}
            />
          </FormField>

          <FormField>
            <Mail className="text-zinc-400 size-5" />

            <FormField.Input
              type="email"
              name="email"
              placeholder="Seu e-mail pessoal"
              value={ownerEmail}
              onChange={(event) => setOwnerEmail(event.target.value)}
            />
          </FormField>

          <Button type="submit" variant="primary" size="full">
            <span>Confirmar criação da viagem</span>
          </Button>
        </form>
      </Modal.Content>
    </Modal>
  );
}
