import { FormEvent, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { DateRange } from 'react-day-picker';
import { DestinationAndDateStep } from './components/steps/destination-and-date-step';
import { InviteGuestsStep } from './components/steps/invite-guests-step';
import { InviteGuestsModal } from './components/modals/invite-guests-modal';
import { ConfirmTripModal } from './components/modals/confirm-trip-modal';
import { CreateTripHeader } from './components/create-trip-header';
import { CreateTripFooter } from './components/create-trip-footer';
import { Alert } from '../../components/alert';
import { api } from '../../lib/axios';
import { validateTripField } from '../../validations/validate-trip-field';
import { validateInviteField } from '../../validations/validate-invite-field';

export function CreateTripPage() {
  const navigate = useNavigate();

  const [isShowAlert, setIsShowAlert] = useState(false);
  const [alertMessage, setAlertMessage] = useState('');

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

  const [isCreatingTrip, setIsCreatingTrip] = useState(false);

  function showAlert(message: string) {
    setAlertMessage(message);
    setIsShowAlert(true);
  }

  function closeAlert() {
    setIsShowAlert(false);
  }

  function openGuestsInput() {
    if (!destination.trim()) {
      showAlert('Preencha o destino para continuar');
      return;
    }
    if (!validateTripField.destination(destination)) {
      showAlert('Destino deve ter ao menos 4 caracteres');
      return;
    }
    if (
      !tripStartAndEndDates ||
      !tripStartAndEndDates.from ||
      !tripStartAndEndDates.to
    ) {
      showAlert('Selecione o período da viagem para continuar');
      return;
    }
    if (!validateTripField.startsAt(tripStartAndEndDates?.from?.toString())) {
      showAlert('Data inicial da viagem inválida');
      return;
    }
    if (!validateTripField.startsAt(tripStartAndEndDates?.to?.toString())) {
      showAlert('Data final da viagem inválida');
      return;
    }
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
    if (!validateTripField.emailsToInvite(emailsToInvite)) {
      showAlert('Um ou mais e-mails de convidados inválido(s)');
      return;
    }
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
      showAlert('Digite um e-mail válido');
      return;
    }
    if (!validateInviteField.email(email)) {
      showAlert('E-mail informado inválido');
      return;
    }
    if (emailsToInvite.includes(email)) {
      showAlert('E-mail informado já está na lista');
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
    if (
      !tripStartAndEndDates ||
      !tripStartAndEndDates.from ||
      !tripStartAndEndDates.to
    ) {
      showAlert('Período da viagem não informado');
      return;
    }
    if (!ownerName.trim()) {
      showAlert('Informe seu nome para continuar');
      return;
    }
    if (!validateTripField.ownerName(ownerName)) {
      showAlert('Nome completo deve ter ao menos 4 caracteres');
      return;
    }
    if (!ownerEmail.trim()) {
      showAlert('Informe seu e-mail para continuar');
      return;
    }
    if (!validateTripField.ownerEmail(ownerEmail)) {
      showAlert('E-mail informado inválido');
      return;
    }
    try {
      setIsCreatingTrip(true);
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
    } catch (error) {
      showAlert('Erro ao tentar cadastrar viagem. Tente novamente mais tarde');
    } finally {
      setIsCreatingTrip(false);
    }
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
          isCreatingTrip={isCreatingTrip}
          closeConfirmTripModal={closeConfirmTripModal}
          createTrip={createTrip}
          setOwnerName={setOwnerName}
          setOwnerEmail={setOwnerEmail}
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
