-- Create a union
CREATE OR REPLACE VIEW beacon_search AS
    SELECT id, hex_id FROM beacon
        UNION
    SELECT id, data->'beacon'->>'hexId' AS hex_id FROM legacy_beacon;