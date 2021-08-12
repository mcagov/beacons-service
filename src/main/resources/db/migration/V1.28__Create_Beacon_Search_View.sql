-- Create a single view of beacons from the beacon and legacy_beacon tables
CREATE OR REPLACE VIEW beacon_search AS
    SELECT id,
           last_modified_date,
           beacon_status,
           hex_id,
           (SELECT full_name FROM person WHERE person.person_type = 'OWNER' AND person.beacon_id = beacon.id) as owner_name
    FROM beacon
        UNION
    SELECT id,
           last_modified_date,
           beacon_status,
           hex_id,
           data->'owner'->>'name' as owner_name
    FROM legacy_beacon;