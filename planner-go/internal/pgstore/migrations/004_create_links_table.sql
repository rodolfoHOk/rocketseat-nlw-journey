-- Write your migrate up statements here

CREATE TABLE IF NOT EXISTS links (
  "id" uuid PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(),
  "title" VARCHAR(255) NOT NULL,
  "url" VARCHAR(255) NOT NULL,
  "trip_id" uuid NOT NULL,

  FOREIGN KEY (trip_id) REFERENCES trips(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

---- create above / drop below ----

DROP TABLE IF EXISTS links;

-- Write your migrate down statements here. If this migration is irreversible
-- Then delete the separator line above.
