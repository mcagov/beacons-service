CREATE TABLE IF NOT EXISTS vessel (
    id uuid PRIMARY KEY,
    mmsi VARCHAR,
    name VARCHAR,
    callsign VARCHAR,
    radio_comms VARCHAR,
    capacity INTEGER,
    vessel_type VARCHAR
)