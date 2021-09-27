UPDATE legacy_beacon SET owner_name = data->'owner'->>'ownerName';
UPDATE legacy_beacon SET owner_email = data->'owner'->>'email';

-- Create legacy beacon search materialized view
CREATE MATERIALIZED VIEW IF NOT EXISTS legacy_beacon_search AS
SELECT id,
       created_date,
       last_modified_date,
       beacon_status,
       hex_id,
       owner_name,
       data->'owner'->>'email' as owner_email,
       (SELECT string_agg(uses->>'useType', ', ') FROM legacy_beacon AS lb, LATERAL jsonb_array_elements(data->'uses') AS uses WHERE lb.id = legacy_beacon.id GROUP BY id) as use_activities
FROM legacy_beacon;

CREATE UNIQUE INDEX legacy_beacon_search_index ON legacy_beacon_search(id);

-- Function to refresh beacon search materialized view for legacy beacons
CREATE OR REPLACE FUNCTION refresh_legacy_beacon_search()
    RETURNS VOID
    LANGUAGE plpgsql
AS $$
BEGIN
    REFRESH MATERIALIZED VIEW CONCURRENTLY legacy_beacon_search;
END $$;
