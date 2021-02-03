CREATE TABLE IF NOT EXISTS beacon_person (
    id uuid PRIMARY KEY,
    beacon_id uuid REFERENCES beacons (id) NOT NULL,
    person_id uuid REFERENCES person (id) NOT NULL
)