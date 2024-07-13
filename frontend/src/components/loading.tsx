import { ComponentProps } from 'react';
import { LoaderCircle } from 'lucide-react';
import clsx from 'clsx';

interface LoadingProps extends ComponentProps<'div'> {
  size?: 'default' | 'lg';
}

export function Loading({
  size = 'default',
  className,
  ...rest
}: LoadingProps) {
  return (
    <div
      className={`flex items-center justify-center text-lime-950 animate-spin ${className}`}
      {...rest}
    >
      <LoaderCircle
        className={clsx({
          'size-5': size === 'default',
          'size-8': size === 'lg',
        })}
      />
    </div>
  );
}
