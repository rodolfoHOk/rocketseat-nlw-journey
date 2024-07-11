import { CircleDashed, UserCog } from 'lucide-react';

export function Guests() {
  return (
    <div className="space-y-6">
      <h2 className="font-semibold text-xl">Convidados</h2>

      <div className="space-y-5">
        <div className="flex items-center justify-between gap-4">
          <div className="space-y-1.5">
            <span className="block font-medium text-zinc-100">
              Jessica White
            </span>

            <span className="block text-sm text-zinc-400 truncate">
              jessica.white44@yahoo.com
            </span>
          </div>

          <CircleDashed className="text-zinc-400 size-5 shrink-0" />
        </div>

        <div className="flex items-center justify-between gap-4">
          <div className="space-y-1.5">
            <span className="block font-medium text-zinc-100">
              Dr. Rita Pacocha
            </span>

            <span className="block text-sm text-zinc-400 truncate">
              lacy.stiedemann@gmail.com
            </span>
          </div>

          <CircleDashed className="text-zinc-400 size-5 shrink-0" />
        </div>
      </div>

      <button className="w-full h-10 bg-zinc-800 text-zinc-200 rounded-lg font-medium flex items-center justify-center gap-2 hover:bg-zinc-700 transition-colors duration-200">
        <UserCog className="size-5" />

        <span>Gerenciar convidados</span>
      </button>
    </div>
  );
}
