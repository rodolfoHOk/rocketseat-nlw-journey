import { ComponentProps } from 'react';
import { LoaderCircle } from 'lucide-react';
import clsx from 'clsx';

interface LoadingProps extends ComponentProps<'div'> {
  color?: 'primary' | 'secondary';
  size?: 'default' | 'lg';
}

export function Loading({
  color = 'primary',
  size = 'default',
  className,
  ...rest
}: LoadingProps) {
  return (
    <div
      className={clsx(
        'flex items-center justify-center animate-spin',
        {
          'text-lime-950': color === 'primary',
          'text-lime-600': color === 'secondary',
        },
        className
      )}
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
