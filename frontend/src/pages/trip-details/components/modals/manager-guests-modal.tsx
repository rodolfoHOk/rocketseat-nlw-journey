import { CalendarCheck2, MailPlus } from 'lucide-react';
import { Button } from '../../../../components/button';
import { Modal } from '../../../../components/modal';

interface ManagerGuestsModalProps {
  closeManagerGuestsModal: () => void;
  openConfirmPresenceModal: () => void;
  openNewInviteModal: () => void;
}

export function ManagerGuestsModal({
  closeManagerGuestsModal,
  openConfirmPresenceModal,
  openNewInviteModal,
}: ManagerGuestsModalProps) {
  return (
    <Modal handleClose={closeManagerGuestsModal}>
      <Modal.Header>
        <Modal.Title>Gerenciar convidados</Modal.Title>

        <Modal.SubTitle>
          Confirme a presença e/ou envie novos convites
        </Modal.SubTitle>
      </Modal.Header>

      <Modal.Content>
        <div className="space-y-6">
          <Button size="full" onClick={openConfirmPresenceModal}>
            <CalendarCheck2 className="size-5" />

            <span>Confirmar presença</span>
          </Button>

          <Button size="full" onClick={openNewInviteModal}>
            <MailPlus className="size-5" />

            <span>Envie novo convite</span>
          </Button>
        </div>
      </Modal.Content>
    </Modal>
  );
}
