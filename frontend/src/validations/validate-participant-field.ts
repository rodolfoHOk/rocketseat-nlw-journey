import { z } from 'zod';

function name(name: string) {
  const schema = z.string().min(3);
  return schema.safeParse(name).success;
}

function email(email: string) {
  const schema = z.string().email();
  return schema.safeParse(email).success;
}

export const validateParticipantField = {
  name,
  email,
};
