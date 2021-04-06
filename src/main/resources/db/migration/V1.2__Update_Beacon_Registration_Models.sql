-- Update beacon tables to reflect correct models
ALTER TABLE beacons
    RENAME TO beacon;

ALTER TABLE beacon
    RENAME COLUMN battery_expiry TO battery_expiry_date;

ALTER TABLE beacon
    RENAME COLUMN last_serviced TO last_serviced_date;

ALTER TABLE beacon
    RENAME COLUMN serial_number TO manufacturer_serial_number;

CREATE INDEX beacon_hex_id_index ON beacon(hex_id);

ALTER TABLE beacon
    ALTER COLUMN beacon_type SET NOT NULL,
    ALTER COLUMN manufacturer SET NOT NULL,
    ALTER COLUMN model SET NOT NULL,
    ALTER COLUMN hex_id SET NOT NULL,
    ALTER COLUMN manufacturer_serial_number SET NOT NULL,
    ADD COLUMN chk_code text,
    ALTER COLUMN beacon_status SET DEFAULT 'DRAFT',
    DROP COLUMN checksum,
    DROP COLUMN coding,
    DROP COLUMN last_modified_date,
    DROP COLUMN protocol_code;

-- Update beacon use table
ALTER TABLE beacon_uses
    RENAME TO beacon_use;

-- Update beacon person table
ALTER TABLE beacon_person
    ADD COLUMN person_type text NOT NULL;

-- Update person table
ALTER TABLE person
    RENAME COLUMN email_address TO email;

ALTER TABLE person
    DROP COLUMN care_of;

ALTER TABLE person
    DROP COLUMN person_type;