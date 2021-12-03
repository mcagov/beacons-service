CREATE OR REPLACE VIEW beacon_search_alt AS
SELECT id,
       created_date,
       last_modified_date,
       (CASE
            WHEN (SELECT COUNT(*)
                  FROM legacy_beacon_claim_event
                  WHERE legacy_beacon_claim_event.legacy_beacon_id = legacy_beacon.id) = 0
                THEN 'MIGRATED'
            ELSE 'DELETED' END) AS beacon_status,
       hex_id,
       owner_name,
       owner_email,
       NULL                     as account_holder_id,
       use_activities
FROM legacy_beacon
UNION ALL
SELECT id,
       created_date,
       last_modified_date,
       beacon_status,
       hex_id,
       (SELECT full_name FROM person WHERE person.person_type = 'OWNER' AND person.beacon_id = beacon.id) AS owner_name,
       (SELECT email
        FROM person
        WHERE person.person_type = 'OWNER'
          AND person.beacon_id = beacon.id)                                                               AS owner_email,
       (SELECT id
        FROM account_holder
        WHERE account_holder.id = beacon.account_holder_id)                                               AS account_holder_id,
       (SELECT REPLACE(string_agg(activity, ', '), '_', ' ')
        FROM beacon_use
        WHERE beacon_use.beacon_id = beacon.id)                                                           AS use_activities
FROM beacon;

CREATE INDEX idx_owner_email_legacy_beacon
ON legacy_beacon (owner_email);
