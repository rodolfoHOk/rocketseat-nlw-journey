import { FormEvent } from 'react';
import { Mail, User, X } from 'lucide-react';

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

            <button>
              <X
                className="size-5 text-zinc-400 hover:text-zinc-300 transition-colors duration-200"
                onClick={closeConfirmTripModal}
              />
            </button>
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
          <div className="h-14 px-4 bg-zinc-950 border border-zinc-800 rounded-lg flex items-center gap-2 hover:opacity-80 transition-opacity duration-200">
            <User className="text-zinc-400 size-5" />

            <input
              type="text"
              name="name"
              placeholder="Seu nome completo"
              className="bg-transparent text-lg placeholder-zinc-400 outline-none flex-1"
            />
          </div>

          <div className="h-14 px-4 bg-zinc-950 border border-zinc-800 rounded-lg flex items-center gap-2 hover:opacity-80 transition-opacity duration-200">
            <Mail className="text-zinc-400 size-5" />

            <input
              type="email"
              name="email"
              placeholder="Seu e-mail pessoal"
              className="bg-transparent text-lg placeholder-zinc-400 outline-none flex-1"
            />
          </div>

          <button
            type="submit"
            className="w-full h-10 bg-lime-300 text-lime-950 rounded-lg font-medium flex items-center justify-center gap-2 hover:bg-lime-400 transition-colors duration-200"
          >
            <span>Confirmar criação da viagem</span>
          </button>
        </form>
      </div>
    </div>
  );
}
