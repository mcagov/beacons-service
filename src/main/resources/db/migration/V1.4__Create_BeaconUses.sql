CREATE TABLE IF NOT EXISTS beaconUses (
    id uuid PRIMARY KEY,
    beaconId uuid REFERENCES beacons (id),
    useType VARCHAR,
    mainUse boolean,
    beaconPersonId uuid REFERENCES beacon_person (id),
    vesselId uuid REFERENCES vessel (id)
)