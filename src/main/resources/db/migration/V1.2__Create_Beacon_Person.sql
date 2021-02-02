CREATE TABLE IF NOT EXISTS beacon_person (
    id SERIAL,
    beaconId INTEGER REFERENCES beacons (id),
    personId INTEGER REFERENCES person (id),
    PRIMARY KEY (id)

)