CREATE TABLE IF NOT EXISTS beacons (
    id uuid PRIMARY KEY,
    beacon_type VARCHAR,
    hex_id VARCHAR,
-- Would it be better to have hexId as Hex type for more efficient queries?
-- Do we want to limit the length to 15 and 23?
    manufacturer VARCHAR,
    model VARCHAR,
    serial_number VARCHAR,
    battery_expiry TIMESTAMP,
    last_serviced TIMESTAMP
)