import { FormEvent } from 'react';
import { AtSign, Plus, X } from 'lucide-react';
import { Modal } from '../../../../components/modal';
import { FormField } from '../../../../components/form-field';
import { Button } from '../../../../components/button';

interface InviteGuestsModalProps {
  emailsToInvite: string[];
  closeGuestsModal: () => void;
  addNewEmailToInvites: (event: FormEvent<HTMLFormElement>) => void;
  removeEmailFromInvites: (email: string) => void;
}

export function InviteGuestsModal({
  emailsToInvite,
  closeGuestsModal,
  addNewEmailToInvites,
  removeEmailFromInvites,
}: InviteGuestsModalProps) {
  return (
    <Modal width="lg" handleClose={closeGuestsModal}>
      <Modal.Header>
        <Modal.Title>Selecionar convidados</Modal.Title>

        <Modal.SubTitle>
          Os convidados irão receber e-mails para confirmar a participação na
          viagem.
        </Modal.SubTitle>
      </Modal.Header>

      <Modal.Content>
        <div className="flex flex-wrap gap-2">
          {emailsToInvite.map((email) => {
            return (
              <div
                key={email}
                className="py-1.5 px-2.5 rounded-md bg-zinc-800 flex items-center gap-2"
              >
                <span className="text-zinc-300">{email}</span>

                <button type="button">
                  <X
                    onClick={() => removeEmailFromInvites(email)}
                    className="size-4 text-zinc-400 hover:text-zinc-300 transition-colors duration-200"
                  />
                </button>
              </div>
            );
          })}
        </div>

        <div className="w-full h-px bg-zinc-800" />

        <form
          onSubmit={addNewEmailToInvites}
          className="p-2.5 bg-zinc-950 border border-zinc-800 rounded-lg flex items-center gap-2"
        >
          <FormField border="borderless" width="flex">
            <AtSign className="text-zinc-400 size-5" />

            <FormField.Input
              type="email"
              name="email"
              placeholder="Digite o email do convidado"
            />
          </FormField>

          <Button type="submit" variant="primary">
            <span>Convidar</span>

            <Plus className="size-5" />
          </Button>
        </form>
      </Modal.Content>
    </Modal>
  );
}
