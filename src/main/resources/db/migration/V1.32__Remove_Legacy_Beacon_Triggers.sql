-- Drop legacy beacon table triggers to speed up the migration

DROP TRIGGER IF EXISTS refresh_beacon_search_on_update_into_legacy_beacons ON legacy_beacon;

DROP TRIGGER IF EXISTS refresh_beacon_search_on_insert_into_legacy_beacons ON legacy_beacon;