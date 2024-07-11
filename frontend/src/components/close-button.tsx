import { X } from 'lucide-react';
import { ComponentProps } from 'react';

interface CloseButtonProps extends ComponentProps<'button'> {
  handleClose: () => void;
}

export function CloseButton({ handleClose, ...rest }: CloseButtonProps) {
  return (
    <button {...rest}>
      <X
        className="size-5 text-zinc-400 hover:text-zinc-300 transition-colors duration-200"
        onClick={handleClose}
      />
    </button>
  );
}
