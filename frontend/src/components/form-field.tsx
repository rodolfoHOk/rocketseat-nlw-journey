import { ComponentProps } from 'react';
import { tv, VariantProps } from 'tailwind-variants';

const fieldVariants = tv({
  base: 'flex items-center gap-2 hover:opacity-80 transition-opacity duration-200',
  variants: {
    border: {
      default: 'h-14 px-4 bg-zinc-950 border border-zinc-800 rounded-lg',
      borderless: '',
    },
    width: {
      default: '',
      flex: 'flex-1',
    },
  },
  defaultVariants: {
    border: 'default',
    width: 'default',
  },
});

interface FormFieldProps
  extends ComponentProps<'div'>,
    VariantProps<typeof fieldVariants> {}

function FormField({ children, border, width, ...rest }: FormFieldProps) {
  return (
    <div className={fieldVariants({ border, width })} {...rest}>
      {children}
    </div>
  );
}

const inputVariants = tv({
  base: 'bg-transparent text-lg placeholder-zinc-400 outline-none',
  variants: {
    inputWidth: {
      flex: 'flex-1',
      fixed: 'w-40',
    },
  },
  defaultVariants: {
    inputWidth: 'flex',
  },
});

interface InputProps
  extends ComponentProps<'input'>,
    VariantProps<typeof inputVariants> {}

function Input({ inputWidth, ...props }: InputProps) {
  return <input className={inputVariants({ inputWidth })} {...props} />;
}

FormField.Input = Input;

export { FormField };
