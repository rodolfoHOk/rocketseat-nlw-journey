import { z } from 'zod';

function title(title: string) {
  const schema = z.string().min(4);
  return schema.safeParse(title).success;
}

function url(url: string) {
  const schema = z.string().url();
  return schema.safeParse(url).success;
}

export const validateLinkField = {
  title,
  url,
};
