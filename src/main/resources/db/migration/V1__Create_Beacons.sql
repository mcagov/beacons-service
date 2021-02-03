CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS beacons (
    id uuid PRIMARY KEY,
    beacon_type text,
    hex_id text,
    manufacturer text,
    model text,
    serial_number text,
    battery_expiry TIMESTAMP,
    last_serviced TIMESTAMP
);