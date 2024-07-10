-- Write your migrate up statements here

CREATE TABLE IF NOT EXISTS trips (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(),
  "destination" VARCHAR(255) NOT NULL,
  "starts_at" TIMESTAMP NOT NULL,
  "ends_at" TIMESTAMP NOT NULL,
  "is_confirmed" BOOLEAN NOT NULL DEFAULT FALSE,
  "owner_email" VARCHAR(255) NOT NULL,
  "owner_name" VARCHAR(255) NOT NULL
);

---- create above / drop below ----

DROP TABLE IF EXISTS trips;

-- Write your migrate down statements here. If this migration is irreversible
-- Then delete the separator line above.
