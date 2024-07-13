import { z } from 'zod';

function title(title: string) {
  const schema = z.string().min(4);
  return schema.safeParse(title).success;
}

function occursAt(occursAt: string) {
  const schema = z.coerce.date();
  return schema.safeParse(occursAt).success;
}

export const validateActivityField = {
  title,
  occursAt,
};
