-- Update beacon tables to reflect correct models
ALTER TABLE beacons
    RENAME TO beacon;

ALTER TABLE beacon
    RENAME COLUMN created_date TO created_at;

ALTER TABLE beacon
    RENAME COLUMN battery_expiry TO battery_expiry_date;

ALTER TABLE beacon
    RENAME COLUMN last_serviced TO last_serviced_date;

ALTER TABLE beacon
    RENAME COLUMN serial_number TO manufacturer_serial_number;

ALTER TABLE beacon
    ALTER COLUMN beacon_type SET NOT NULL,
    ALTER COLUMN manufacturer SET NOT NULL,
    ALTER COLUMN model SET NOT NULL,
    ALTER COLUMN hex_id SET NOT NULL,
    ALTER COLUMN manufacturer_serial_number SET NOT NULL,
    DROP COLUMN checksum,
    DROP COLUMN coding,
    DROP COLUMN last_modified_date,
    DROP COLUMN protocol_code;

-- Update beacon use table
ALTER TABLE beacon_uses
    RENAME TO beacon_use;
