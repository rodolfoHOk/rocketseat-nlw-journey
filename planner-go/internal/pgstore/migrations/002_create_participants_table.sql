-- Write your migrate up statements here

CREATE TABLE IF NOT EXISTS participants (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(),
  "name" VARCHAR(255) NOT NULL DEFAULT '',
  "email" VARCHAR(255) NOT NULL,
  "is_confirmed" BOOLEAN NOT NULL DEFAULT FALSE,
  "trip_id" uuid NOT NULL,

  FOREIGN KEY (trip_id) REFERENCES trips(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

---- create above / drop below ----

DROP TABLE IF EXISTS participants;

-- Write your migrate down statements here. If this migration is irreversible
-- Then delete the separator line above.
