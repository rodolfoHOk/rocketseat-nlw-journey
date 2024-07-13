import { useEffect, useState } from 'react';
import { CreateActivityModal } from './components/modals/create-activity-modal';
import { ImportantLinks } from './components/important-links';
import { Guests } from './components/guests';
import { Activities } from './components/activities';
import { DestinationAndDateHeader } from './components/destination-and-date-header';
import { CreateLinkModal } from './components/modals/create-link-modal';
import { ManagerGuestsModal } from './components/modals/manager-guests-modal';
import { ConfirmPresenceModal } from './components/modals/confirm-presence-modal';
import { useSearchParams } from 'react-router-dom';
import { NewInviteModal } from './components/modals/new-invite-modal';
import { UpdateTripModal } from './components/modals/update-trip-modal';
import { Alert } from '../../components/alert';

export function TripDetailsPage() {
  const [urlParams, _] = useSearchParams();

  const [isUpdateTripModalOpen, setIsUpdateTripModalOpen] = useState(false);
  const [isCreateActivityModalOpen, setIsCreateActivityModalOpen] =
    useState(false);
  const [isCreateLinkModalOpen, setIsCreateLinkModalOpen] = useState(false);
  const [isManagerGuestsModalOpen, setIsManagerGuestsModalOpen] =
    useState(false);
  const [isConfirmPresenceModalOpen, setIsConfirmPresenceModalOpen] =
    useState(false);
  const [isNewInviteModalOpen, setIsNewInviteModalOpen] = useState(false);

  const [isShowAlert, setIsShowAlert] = useState(false);
  const [alertMessage, setAlertMessage] = useState('');

  function openUpdateTripModal() {
    closeManagerGuestsModal();
    setIsUpdateTripModalOpen(true);
  }

  function closeUpdateTripModal() {
    setIsUpdateTripModalOpen(false);
  }

  function openCreateActivityModal() {
    setIsCreateActivityModalOpen(true);
  }

  function closeCreateActivityModal() {
    setIsCreateActivityModalOpen(false);
  }

  function openCreateLinkModal() {
    setIsCreateLinkModalOpen(true);
  }

  function closeCreateLinkModal() {
    setIsCreateLinkModalOpen(false);
  }

  function openManagerGuestsModal() {
    setIsManagerGuestsModalOpen(true);
  }

  function closeManagerGuestsModal() {
    setIsManagerGuestsModalOpen(false);
  }

  function openConfirmPresenceModal() {
    closeManagerGuestsModal();
    setIsConfirmPresenceModalOpen(true);
  }

  function closeConfirmPresenceModal() {
    setIsConfirmPresenceModalOpen(false);
  }

  function openNewInviteModal() {
    closeManagerGuestsModal();
    setIsNewInviteModalOpen(true);
  }

  function closeNewInviteModal() {
    setIsNewInviteModalOpen(false);
  }

  function showAlert(message: string) {
    setAlertMessage(message);
    setIsShowAlert(true);
  }

  function closeAlert() {
    setIsShowAlert(false);
  }

  useEffect(() => {
    if (urlParams.get('participant')) {
      openConfirmPresenceModal();
    }
  }, []);

  return (
    <div className="max-w-6xl px-6 py-10 mx-auto space-y-8">
      <DestinationAndDateHeader openUpdateTripModal={openUpdateTripModal} />

      <main className="flex gap-16 px-4">
        <Activities openCreateActivityModal={openCreateActivityModal} />

        <div className="w-80 space-y-6">
          <ImportantLinks openCreateLinkModal={openCreateLinkModal} />

          <div className="w-full h-px bg-zinc-800" />

          <Guests openManagerGuestsModal={openManagerGuestsModal} />
        </div>
      </main>

      {isUpdateTripModalOpen && (
        <UpdateTripModal
          closeUpdateTripModal={closeUpdateTripModal}
          showAlert={showAlert}
        />
      )}

      {isCreateActivityModalOpen && (
        <CreateActivityModal
          closeCreateActivityModal={closeCreateActivityModal}
          showAlert={showAlert}
        />
      )}

      {isCreateLinkModalOpen && (
        <CreateLinkModal
          closeCreateLinkModal={closeCreateLinkModal}
          showAlert={showAlert}
        />
      )}

      {isManagerGuestsModalOpen && (
        <ManagerGuestsModal
          closeManagerGuestsModal={closeManagerGuestsModal}
          openConfirmPresenceModal={openConfirmPresenceModal}
          openNewInviteModal={openNewInviteModal}
        />
      )}

      {isConfirmPresenceModalOpen && (
        <ConfirmPresenceModal
          closeConfirmPresenceModal={closeConfirmPresenceModal}
          showAlert={showAlert}
        />
      )}

      {isNewInviteModalOpen && (
        <NewInviteModal
          closeNewInviteModal={closeNewInviteModal}
          showAlert={showAlert}
        />
      )}

      {isShowAlert && (
        <Alert
          title="Plann.er erro"
          message={alertMessage}
          closeAlert={closeAlert}
        />
      )}
    </div>
  );
}
