import { AlertOctagon } from 'lucide-react';
import { CloseButton } from './close-button';

interface AlertProps {
  title: string;
  message: string;
  closeAlert: () => void;
}

export function Alert({ title, message, closeAlert }: AlertProps) {
  setTimeout(() => {
    closeAlert();
  }, 3000);

  return (
    <div className="fixed top-16 right-1/2 translate-x-1/2 px-4 py-3 bg-zinc-900 flex flex-row items-center gap-3 rounded-lg shadow-shape">
      <AlertOctagon className="size-8 text-red-500" />

      <div className="space-y-2">
        <div className="flex items-center justify-between">
          <h2 className="text-lg font-semibold">{title}</h2>

          <CloseButton handleClose={closeAlert} />
        </div>

        <p className="text-zinc-400">{message}</p>
      </div>
    </div>
  );
}
