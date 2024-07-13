import { z } from 'zod';

function destination(destination: string) {
  const schema = z.string().min(4);
  return schema.safeParse(destination).success;
}

function startsAt(startsAt: string) {
  const schema = z.coerce.date();
  return schema.safeParse(startsAt).success;
}

function endsAt(endsAt: string) {
  const schema = z.coerce.date();
  return schema.safeParse(endsAt).success;
}

function emailsToInvite(emailsToInvite: string[]) {
  const schema = z.array(z.string().email());
  return schema.safeParse(emailsToInvite).success;
}

function ownerName(ownerName: string) {
  const schema = z.string().min(4);
  return schema.safeParse(ownerName).success;
}

function ownerEmail(ownerEmail: string) {
  const schema = z.string().email();
  return schema.safeParse(ownerEmail).success;
}

export const validateTripField = {
  destination,
  startsAt,
  endsAt,
  emailsToInvite,
  ownerName,
  ownerEmail,
};
