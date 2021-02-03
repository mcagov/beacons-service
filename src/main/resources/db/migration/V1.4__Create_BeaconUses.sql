CREATE TABLE IF NOT EXISTS beacon_uses (
    id uuid PRIMARY KEY,
    beacon_id uuid REFERENCES beacons (id),
    use_type VARCHAR,
    main_use boolean,
    beacon_person_id uuid REFERENCES beacon_person (id),
    vessel_id uuid REFERENCES vessel (id)
)