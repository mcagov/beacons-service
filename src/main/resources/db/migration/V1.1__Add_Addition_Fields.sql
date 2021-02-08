ALTER TABLE beacons
    ADD COLUMN chk_code text,
    ADD COLUMN created_date TIMESTAMP,
    ADD COLUMN last_modified_date TIMESTAMP;

ALTER TABLE person
    ADD COLUMN created_date TIMESTAMP,
    ADD COLUMN last_modified_date TIMESTAMP;

ALTER TABLE beacon_person
    ADD COLUMN created_date TIMESTAMP,
    ADD COLUMN last_modified_date TIMESTAMP;

ALTER TABLE vessel
    ADD COLUMN created_date TIMESTAMP,
    ADD COLUMN last_modified_date TIMESTAMP;

ALTER TABLE beacon_uses
    ADD COLUMN location text,
    ADD COLUMN created_date TIMESTAMP,
    ADD COLUMN last_modified_date TIMESTAMP;