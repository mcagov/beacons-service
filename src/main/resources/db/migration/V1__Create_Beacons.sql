CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS beacons (
    id uuid PRIMARY KEY,
    beacon_type VARCHAR,
    hex_id VARCHAR,
    manufacturer VARCHAR,
    model VARCHAR,
    serial_number VARCHAR,
    battery_expiry TIMESTAMP,
    last_serviced TIMESTAMP
);