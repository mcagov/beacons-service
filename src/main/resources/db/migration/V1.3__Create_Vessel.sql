CREATE TABLE IF NOT EXISTS vessel (
    id uuid,
    mmsi VARCHAR,
    name VARCHAR,
    callsign VARCHAR,
    radioComms VARCHAR,
    capacity INTEGER,
    vesselType VARCHAR,
    PRIMARY KEY (id)
)