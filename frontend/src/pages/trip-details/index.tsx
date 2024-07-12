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

export function TripDetailsPage() {
  const [urlParams, _] = useSearchParams({ participantId: '' });

  const [isCreateActivityModalOpen, setIsCreateActivityModalOpen] =
    useState(false);
  const [isCreateLinkModalOpen, setIsCreateLinkModalOpen] = useState(false);
  const [isManagerGuestsModalOpen, setIsManagerGuestsModalOpen] =
    useState(false);
  const [isConfirmPresenceModalOpen, setIsConfirmPresenceModalOpen] =
    useState(false);

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

  useEffect(() => {
    if (urlParams.get('participantId')) {
      openConfirmPresenceModal();
    }
  }, []);

  return (
    <div className="max-w-6xl px-6 py-10 mx-auto space-y-8">
      <DestinationAndDateHeader />

      <main className="flex gap-16 px-4">
        <Activities openCreateActivityModal={openCreateActivityModal} />

        <div className="w-80 space-y-6">
          <ImportantLinks openCreateLinkModal={openCreateLinkModal} />

          <div className="w-full h-px bg-zinc-800" />

          <Guests openManagerGuestsModal={openManagerGuestsModal} />
        </div>
      </main>

      {isCreateActivityModalOpen && (
        <CreateActivityModal
          closeCreateActivityModal={closeCreateActivityModal}
        />
      )}

      {isCreateLinkModalOpen && (
        <CreateLinkModal closeCreateLinkModal={closeCreateLinkModal} />
      )}

      {isManagerGuestsModalOpen && (
        <ManagerGuestsModal
          closeManagerGuestsModal={closeManagerGuestsModal}
          openConfirmPresenceModal={openConfirmPresenceModal}
          openNewInviteModal={() => console.log('todo: open new invite modal')}
        />
      )}

      {isConfirmPresenceModalOpen && (
        <ConfirmPresenceModal
          closeConfirmPresenceModal={closeConfirmPresenceModal}
        />
      )}
    </div>
  );
}
