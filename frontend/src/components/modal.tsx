'use client';

import { ComponentProps, createContext, useContext } from 'react';
import { tv, VariantProps } from 'tailwind-variants';
import { CloseButton } from './close-button';

interface closeContextProps {
  handleClose: () => void;
}

const CloseContext = createContext({} as closeContextProps);

const modalVariants = tv({
  base: 'rounded-xl py-5 px-6 shadow-shape bg-zinc-900 space-y-5',
  variants: {
    width: {
      default: 'w-[540px]',
      fit: 'w-fit',
      lg: 'w-[640px]',
    },
  },
  defaultVariants: {
    width: 'default',
  },
});

interface ModalProps
  extends ComponentProps<'div'>,
    VariantProps<typeof modalVariants> {
  handleClose: () => void;
}

function Modal({ width, children, handleClose }: ModalProps) {
  return (
    <div className="fixed inset-0 bg-black/60 flex items-center justify-center">
      <div className={modalVariants({ width })}>
        <CloseContext.Provider value={{ handleClose }}>
          {children}
        </CloseContext.Provider>
      </div>
    </div>
  );
}

function Header({ children, ...rest }: ComponentProps<'div'>) {
  return (
    <div className="space-y-2" {...rest}>
      {children}
    </div>
  );
}

function Title({ children, ...rest }: ComponentProps<'h2'>) {
  const { handleClose } = useContext(CloseContext);

  return (
    <div className="flex items-center justify-between">
      <h2 className="text-lg font-semibold" {...rest}>
        {children}
      </h2>

      <CloseButton handleClose={handleClose} />
    </div>
  );
}

function SubTitle({ children, ...rest }: ComponentProps<'p'>) {
  return (
    <p className="text-sm text-zinc-400" {...rest}>
      {children}
    </p>
  );
}

function Content({ children, ...rest }: ComponentProps<'div'>) {
  return (
    <div className="space-y-5" {...rest}>
      {children}
    </div>
  );
}

Modal.Header = Header;
Modal.Title = Title;
Modal.SubTitle = SubTitle;
Modal.Content = Content;

export { Modal };
