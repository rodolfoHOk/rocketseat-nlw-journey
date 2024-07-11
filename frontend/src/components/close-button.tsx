import { X } from 'lucide-react';

interface CloseButtonProps {
  handleClose: () => void;
}

export function CloseButton({ handleClose }: CloseButtonProps) {
  return (
    <button>
      <X
        className="size-5 text-zinc-400 hover:text-zinc-300 transition-colors duration-200"
        onClick={handleClose}
      />
    </button>
  );
}
