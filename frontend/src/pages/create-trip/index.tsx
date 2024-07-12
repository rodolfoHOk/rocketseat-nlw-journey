import { FormEvent, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { DateRange } from 'react-day-picker';
import { DestinationAndDateStep } from './components/steps/destination-and-date-step';
import { InviteGuestsStep } from './components/steps/invite-guests-step';
import { InviteGuestsModal } from './components/modals/invite-guests-modal';
import { ConfirmTripModal } from './components/modals/confirm-trip-modal';
import { CreateTripHeader } from './components/create-trip-header';
import { CreateTripFooter } from './components/create-trip-footer';
import { api } from '../../lib/axios';

export function CreateTripPage() {
  const navigate = useNavigate();

  const [isGuestsInputOpen, setIsGuestsInputOpen] = useState(false);
  const [isGuestsModalOpen, setIsGuestsModalOpen] = useState(false);
  const [isConfirmTripModalOpen, setIsConfirmTripModalOpen] = useState(false);

  const [destination, setDestination] = useState('');
  const [tripStartAndEndDates, setTripStartAndEndDates] = useState<
    DateRange | undefined
  >();
  const [emailsToInvite, setEmailsToInvite] = useState<string[]>([]);
  const [ownerName, setOwnerName] = useState('');
  const [ownerEmail, setOwnerEmail] = useState('');

  function openGuestsInput() {
    setIsGuestsInputOpen(true);
  }

  function closeGuestsInput() {
    setIsGuestsInputOpen(false);
  }

  function openGuestsModal() {
    setIsGuestsModalOpen(true);
  }

  function closeGuestsModal() {
    setIsGuestsModalOpen(false);
  }

  function openConfirmTripModal() {
    setIsConfirmTripModalOpen(true);
  }

  function closeConfirmTripModal() {
    setIsConfirmTripModalOpen(false);
  }

  function addNewEmailToInvites(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const email = data.get('email')?.toString();
    if (!email) {
      return;
    }
    if (emailsToInvite.includes(email)) {
      return;
    }
    setEmailsToInvite((prevState) => [...prevState, email]);
    event.currentTarget.reset();
  }

  function removeEmailFromInvites(emailToRemove: string) {
    const newEmailList = emailsToInvite.filter(
      (email) => email !== emailToRemove
    );
    setEmailsToInvite(newEmailList);
  }

  async function createTrip(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    if (!destination) {
      return;
    }
    if (!tripStartAndEndDates?.from || !tripStartAndEndDates?.to) {
      return;
    }
    if (emailsToInvite.length === 0) {
      return;
    }
    if (!ownerName || !ownerEmail) {
      return;
    }
    const response = await api.post('/trips', {
      destination,
      starts_at: tripStartAndEndDates.from,
      ends_at: tripStartAndEndDates.to,
      emails_to_invite: emailsToInvite,
      owner_name: ownerName,
      owner_email: ownerEmail,
    });
    const { tripId } = response.data;
    navigate(`/trips/${tripId}`);
  }

  return (
    <div className="h-screen flex items-center justify-center bg-pattern bg-no-repeat bg-center">
      <div className="max-w-3xl w-full px-6 text-center space-y-10">
        <CreateTripHeader />

        <div className="space-y-4">
          <DestinationAndDateStep
            isGuestsInputOpen={isGuestsInputOpen}
            destination={destination}
            tripStartAndEndDates={tripStartAndEndDates}
            openGuestsInput={openGuestsInput}
            closeGuestsInput={closeGuestsInput}
            setDestination={setDestination}
            setTripStartAndEndDates={setTripStartAndEndDates}
          />

          {isGuestsInputOpen && (
            <InviteGuestsStep
              emailsToInvite={emailsToInvite}
              openGuestsModal={openGuestsModal}
              openConfirmTripModal={openConfirmTripModal}
            />
          )}
        </div>

        <CreateTripFooter />
      </div>

      {isGuestsModalOpen && (
        <InviteGuestsModal
          emailsToInvite={emailsToInvite}
          closeGuestsModal={closeGuestsModal}
          addNewEmailToInvites={addNewEmailToInvites}
          removeEmailFromInvites={removeEmailFromInvites}
        />
      )}

      {isConfirmTripModalOpen && (
        <ConfirmTripModal
          destination={destination}
          tripStartAndEndDates={tripStartAndEndDates}
          ownerName={ownerName}
          ownerEmail={ownerEmail}
          closeConfirmTripModal={closeConfirmTripModal}
          createTrip={createTrip}
          setOwnerName={setOwnerName}
          setOwnerEmail={setOwnerEmail}
        />
      )}
    </div>
  );
}
