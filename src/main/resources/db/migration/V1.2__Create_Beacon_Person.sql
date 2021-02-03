CREATE TABLE IF NOT EXISTS beacon_person (
    id uuid PRIMARY KEY,
    beacon_id uuid REFERENCES beacons (id),
    person_id uuid REFERENCES person (id)
)