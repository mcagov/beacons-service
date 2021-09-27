-- Drop as REPLACE fails to convert existing view
DROP VIEW beacon_search;

CREATE VIEW beacon_search AS
SELECT id,
       created_date,
       last_modified_date,
       beacon_status,
       'migrated' as beacon_type,
       hex_id,
       owner_name,
       owner_email,
       NULL as account_holder_id,
       use_activities
FROM legacy_beacon
UNION ALL
SELECT id,
       created_date,
       last_modified_date,
       beacon_status,
       'new' as beacon_type,
       hex_id,
       (SELECT full_name FROM person WHERE person.person_type = 'OWNER' AND person.beacon_id = beacon.id) AS owner_name,
       (SELECT email FROM person WHERE person.person_type = 'OWNER' AND person.beacon_id = beacon.id) AS owner_email,
       (SELECT id FROM account_holder WHERE account_holder.id = beacon.account_holder_id) AS account_holder_id,
       (SELECT REPLACE(string_agg(activity, ', '), '_', ' ') FROM beacon_use WHERE beacon_use.beacon_id = beacon.id) AS use_activities
FROM beacon;

