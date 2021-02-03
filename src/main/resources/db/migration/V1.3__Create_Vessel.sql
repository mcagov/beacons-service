CREATE TABLE IF NOT EXISTS vessel (
    id uuid PRIMARY KEY,
    mmsi VARCHAR,
    name VARCHAR,
    callsign VARCHAR,
    radioComms VARCHAR,
    capacity INTEGER,
    vesselType VARCHAR
)