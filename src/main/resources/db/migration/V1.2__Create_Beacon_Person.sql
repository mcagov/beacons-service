CREATE TABLE IF NOT EXISTS beacon_person (
    id uuid PRIMARY KEY,
    beaconId uuid REFERENCES beacons (id),
    personId uuid REFERENCES person (id)
)