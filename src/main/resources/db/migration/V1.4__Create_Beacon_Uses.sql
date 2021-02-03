CREATE TABLE IF NOT EXISTS beacon_uses (
    id uuid PRIMARY KEY,
    beacon_id uuid REFERENCES beacons (id) NOT NULL,
    use_type VARCHAR,
--     TODO: Think about what the best default value would be for main use
    main_use boolean NOT NULL DEFAULT false,
    beacon_person_id uuid REFERENCES beacon_person (id),
    vessel_id uuid REFERENCES vessel (id)
);