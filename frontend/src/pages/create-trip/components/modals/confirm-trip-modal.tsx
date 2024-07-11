import { FormEvent } from 'react';
import { Mail, User } from 'lucide-react';
import { CloseButton } from '../../../../components/close-button';
import { Button } from '../../../../components/button';
import { FormField } from '../../../../components/form-field';

interface ConfirmTripModalProps {
  closeConfirmTripModal: () => void;
  createTrip: (event: FormEvent<HTMLFormElement>) => void;
}

export function ConfirmTripModal({
  closeConfirmTripModal,
  createTrip,
}: ConfirmTripModalProps) {
  return (
    <div className="fixed inset-0 bg-black/60 flex items-center justify-center">
      <div className="w-[540px] rounded-xl py-5 px-6 shadow-shape bg-zinc-900 space-y-5">
        <div className="space-y-2">
          <div className="flex items-center justify-between">
            <h2 className="font-lg font-semibold">
              Confirmar criação de viagem
            </h2>

            <CloseButton handleClose={closeConfirmTripModal} />
          </div>

          <p className="text-sm text-zinc-400">
            {`Para concluir a criação da viagem para `}
            <span className="font-semibold text-zinc-100">
              {`Florianópolis, Brasil`}
            </span>
            {` nas datas de `}
            <span className="font-semibold text-zinc-100">
              {`16 a 27 de Agosto de 2024`}
            </span>
            {` preencha seus dados abaixo:`}
          </p>
        </div>

        <form onSubmit={createTrip} className="space-y-3">
          <FormField>
            <User className="text-zinc-400 size-5" />

            <FormField.Input
              type="text"
              name="name"
              placeholder="Seu nome completo"
            />
          </FormField>

          <FormField>
            <Mail className="text-zinc-400 size-5" />

            <FormField.Input
              type="email"
              name="email"
              placeholder="Seu e-mail pessoal"
            />
          </FormField>

          <Button type="submit" variant="primary" size="full">
            <span>Confirmar criação da viagem</span>
          </Button>
        </form>
      </div>
    </div>
  );
}
