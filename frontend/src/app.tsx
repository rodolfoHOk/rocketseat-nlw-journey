import { FormEvent, useState } from 'react';
import {
  ArrowRight,
  AtSign,
  Calendar,
  MapPin,
  Plus,
  Settings2,
  UserRoundPlus,
  X,
} from 'lucide-react';

export function App() {
  const [isGuestsInputOpen, setIsGuestsInputOpen] = useState(false);
  const [isGuestsModalOpen, setIsGuestsModalOpen] = useState(false);

  const [emailsToInvite, setEmailsToInvite] = useState<string[]>([
    'jessica.white44@yahoo.com',
    'erik_leffler3@gmail.com',
    'rebekah.conn21@gmail.com',
    'emile.mayer25@yahoo.com',
    'justus_hessel81@hotmail.com',
    'hellen_graham@yahoo.com',
    'kole.schiller27@yahoo.com',
  ]);

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

  return (
    <div className="h-screen flex items-center justify-center bg-pattern bg-no-repeat bg-center">
      <div className="max-w-3xl w-full px-6 text-center space-y-10">
        <div className="flex flex-col items-center gap-3">
          <img src="/logo.svg" alt="plann.er" />

          <p className="text-zinc-300 text-lg">
            Convide seus amigos e planeje sua próxima viagem!
          </p>
        </div>

        <div className="space-y-4">
          <div className="h-16 bg-zinc-900 px-4 rounded-xl flex items-center gap-3 shadow-shape">
            <div className="flex items-center gap-2 flex-1">
              <MapPin className="size-5 text-zinc-400" />

              <input
                type="text"
                placeholder="Para onde você vai?"
                className="bg-transparent text-lg placeholder-zinc-400 outline-none flex-1"
                disabled={isGuestsInputOpen}
              />
            </div>

            <div className="flex items-center gap-2">
              <Calendar className="size-5 text-zinc-400" />

              <input
                type="text"
                placeholder="Quando?"
                className="w-40 bg-transparent text-lg placeholder-zinc-400 outline-none"
                disabled={isGuestsInputOpen}
              />
            </div>

            <div className="w-px h-6 bg-zinc-800" />

            {isGuestsInputOpen ? (
              <button
                onClick={closeGuestsInput}
                className="bg-zinc-800 text-zinc-200 rounded-lg px-5 py-2 font-medium flex items-center gap-2 hover:bg-zinc-700 transition-colors duration-200"
              >
                <span>Alterar local/data</span>

                <Settings2 className="size-5" />
              </button>
            ) : (
              <button
                onClick={openGuestsInput}
                className="bg-lime-300 text-lime-950 rounded-lg px-5 py-2 font-medium flex items-center gap-2 hover:bg-lime-400 transition-colors duration-200"
              >
                <span>Continuar</span>

                <ArrowRight className="size-5" />
              </button>
            )}
          </div>

          {isGuestsInputOpen && (
            <div className="h-16 bg-zinc-900 px-4 rounded-xl flex items-center gap-3 shadow-shape">
              <button
                type="button"
                onClick={openGuestsModal}
                className="flex items-center gap-2 flex-1 text-left"
              >
                <UserRoundPlus className="size-5 text-zinc-400" />

                <span className="text-lg text-zinc-400 flex-1">
                  Quem estará na viagem?
                </span>
              </button>

              <div className="w-px h-6 bg-zinc-800" />

              <button className="bg-lime-300 text-lime-950 rounded-lg px-5 py-2 font-medium flex items-center gap-2 hover:bg-lime-400 transition-colors duration-200">
                <span>Confirmar viagem</span>

                <ArrowRight className="size-5" />
              </button>
            </div>
          )}
        </div>

        <p className="text-sm text-zinc-500">
          <span>
            Ao planejar sua viagem pela plann.er você automaticamente concorda
          </span>
          <br />
          <span>{`com nossos `}</span>
          <a className="text-zinc-300 underline" href="#">
            termos de uso
          </a>
          <span>{` e `}</span>
          <a className="text-zinc-300 underline" href="#">
            políticas de privacidade
          </a>
          <span>.</span>
        </p>
      </div>

      {isGuestsModalOpen && (
        <div className="fixed inset-0 bg-black/60 flex items-center justify-center">
          <div className="w-[640px] rounded-xl py-5 px-6 shadow-shape bg-zinc-900 space-y-5">
            <div className="space-y-2">
              <div className="flex items-center justify-between">
                <h2 className="font-lg font-semibold">Selecionar convidados</h2>

                <button>
                  <X
                    className="size-5 text-zinc-400 hover:text-zinc-300 transition-colors duration-200"
                    onClick={closeGuestsModal}
                  />
                </button>
              </div>

              <p className="text-sm text-zinc-400">
                Os convidados irão receber e-mails para confirmar a participação
                na viagem.
              </p>
            </div>

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
              <div className="px-2 flex items-center flex-1 gap-2">
                <AtSign className="text-zinc-400 size-5" />

                <input
                  type="email"
                  name="email"
                  placeholder="Digite o email do convidado"
                  className="bg-transparent text-lg placeholder-zinc-400 outline-none flex-1"
                />
              </div>

              <button
                type="submit"
                className="bg-lime-300 text-lime-950 rounded-lg px-5 py-2 font-medium flex items-center gap-2 hover:bg-lime-400"
              >
                <span>Convidar</span>

                <Plus className="size-5" />
              </button>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}
