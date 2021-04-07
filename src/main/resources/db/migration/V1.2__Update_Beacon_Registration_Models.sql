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

ALTER TABLE beacon_use
    RENAME COLUMN use_type TO environment;

ALTER TABLE beacon_use
    ALTER COLUMN environment SET NOT NULL,
    ADD COLUMN other_environment_use text,
    ADD COLUMN activity text NOT NULL,
    ADD COLUMN other_activity text,
    ADD COLUMN call_sign text,
    ADD COLUMN purpose text,
    ADD COLUMN description text,
    ADD COLUMN vessel_mmsi text,
    ADD COLUMN radio_comms text,
    ADD COLUMN max_capacity text,
    ADD COLUMN vessel_type text,
    ADD COLUMN ssr_number text,
    ADD COLUMN homeport text,
    ADD COLUMN area_of_operation text,
    ADD COLUMN aircraft_manufacturer text,
    ADD COLUMN principal_airport text,
    ADD COLUMN secondary_airport text,
    ADD COLUMN registration_mark text,
    ADD COLUMN hex_address text,
    ADD COLUMN cn_or_msn_number text,
    ADD COLUMN dongle boolean,
    DROP COLUMN last_modified_date,
    DROP COLUMN beacon_person_id,
    DROP COLUMN vessel_id;

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

-- Drop table vessel and capture all fields on the beacon_use table until we allow users to create/manage vessels/aircrafts
DROP TABLE vessel;

-- Drop telephone table
DROP TABLE telephone;

