-- Create a union
CREATE OR REPLACE VIEW beacon_search AS
    SELECT id FROM beacon
        UNION
    SELECT id FROM legacy_beacon;