import { ComponentProps, ReactNode } from 'react';
import { tv, VariantProps } from 'tailwind-variants';
import { Loading } from './loading';

const buttonVariants = tv({
  base: 'px-5 rounded-lg flex items-center justify-center gap-2 font-medium disabled:opacity-50 transition-colors duration-200',
  variants: {
    variant: {
      primary:
        'bg-lime-300 text-lime-950 hover:bg-lime-400 disabled:bg-lime-300',
      secondary:
        'bg-zinc-800 text-zinc-200 hover:bg-zinc-700 disabled:bg-zinc-800',
    },
    size: {
      default: 'py-2',
      full: 'w-full h-11',
    },
  },
  defaultVariants: {
    variant: 'primary',
    size: 'default',
  },
});

interface ButtonProps
  extends ComponentProps<'button'>,
    VariantProps<typeof buttonVariants> {
  children: ReactNode;
  isLoading?: boolean;
}

export function Button({
  isLoading = false,
  children,
  variant,
  size,
  ...rest
}: ButtonProps) {
  return (
    <button
      className={buttonVariants({ variant, size })}
      disabled={isLoading}
      {...rest}
    >
      {isLoading ? <Loading /> : children}
    </button>
  );
}
