import { useState } from 'react';
import { CreateActivityModal } from './components/modals/create-activity-modal';
import { ImportantLinks } from './components/important-links';
import { Guests } from './components/guests';
import { Activities } from './components/activities';
import { DestinationAndDateHeader } from './components/destination-and-date-header';
import { CreateLinkModal } from './components/modals/create-link-modal';
import { ManagerGuestsModal } from './components/modals/manager-guests-modal';

export function TripDetailsPage() {
  const [isCreateActivityModalOpen, setIsCreateActivityModalOpen] =
    useState(false);
  const [isCreateLinkModalOpen, setIsCreateLinkModalOpen] = useState(false);
  const [isManagerGuestsModalOpen, setIsManagerGuestsModalOpen] =
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
          openConfirmPresenceModal={() =>
            console.log('todo: open confirm presence modal')
          }
          openNewInviteModal={() => console.log('todo: open new invite modal')}
        />
      )}
    </div>
  );
}
