CREATE TABLE IF NOT EXISTS legacy_beacon_records (
    id uuid PRIMARY KEY,
    "value" jsonb NOT NULL,
    hex_id text NOT NULL,
    owner_email text,
    created_date timestamp NOT NULL,
    last_modifiedd timestamp NOT NULL
)