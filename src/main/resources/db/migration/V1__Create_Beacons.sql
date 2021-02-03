CREATE TABLE IF NOT EXISTS beacons (
    id uuid PRIMARY KEY,
    beaconType VARCHAR,
    hexId VARCHAR,
-- Would it be better to have hexId as Hex type for more efficient queries?
-- Do we want to limit the length to 15 and 23?
    manufacturer VARCHAR,
    model VARCHAR,
    serialNumber VARCHAR,
    batteryExpiry TIMESTAMP,
    lastServiced TIMESTAMP
)