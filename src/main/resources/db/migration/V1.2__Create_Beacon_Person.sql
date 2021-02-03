CREATE TABLE IF NOT EXISTS beacon_person (
    id uuid,
    beaconId uuid REFERENCES beacons (id),
    personId uuid REFERENCES person (id),
    PRIMARY KEY (id)

)