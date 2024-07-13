import { z } from 'zod';

function email(email: string) {
  const schema = z.string().email();
  return schema.safeParse(email).success;
}

export const validateInviteField = {
  email,
};
