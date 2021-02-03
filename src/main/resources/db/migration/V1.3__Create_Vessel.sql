CREATE TABLE IF NOT EXISTS vessel (
    id uuid PRIMARY KEY,
    mmsi text,
    name text,
    callsign text,
    radio_comms text,
    capacity INTEGER,
    vessel_type text
);