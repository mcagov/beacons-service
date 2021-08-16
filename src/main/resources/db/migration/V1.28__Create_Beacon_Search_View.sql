-- Create a single view of beacons from the beacon and legacy_beacon tables
CREATE OR REPLACE VIEW beacon_search AS
    SELECT id,
           last_modified_date,
           beacon_status,
           hex_id,
           (SELECT full_name FROM person WHERE person.person_type = 'OWNER' AND person.beacon_id = beacon.id) AS owner_name,
           (SELECT string_agg(activity, ', ') FROM beacon_use WHERE beacon_use.beacon_id = beacon.id) AS use_activities
    FROM beacon
        UNION
    SELECT id,
           last_modified_date,
           beacon_status,
           hex_id,
           data->'owner'->>'ownerName' as owner_name,
           (SELECT string_agg(use->>'useType', ', ') FROM legacy_beacon CROSS JOIN jsonb_array_elements(data->'uses') AS use) AS use_activities
    FROM legacy_beacon;