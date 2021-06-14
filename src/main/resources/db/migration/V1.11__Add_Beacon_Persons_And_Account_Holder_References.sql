ALTER TABLE beacon_person
    RENAME TO person;

ALTER TABLE beacon
    ADD COLUMN account_id uuid REFERENCES beacon_account_holder (id),
    ADD COLUMN owner_id uuid REFERENCES person (id);

UPDATE beacon
    SET owner_id = person.id
    FROM person
    WHERE person.beacon_id = beacon.id AND person.person_type = 'OWNER';

CREATE TABLE IF NOT EXISTS emergency_contact_linker (
                                             id uuid PRIMARY KEY,
                                             beacon_id uuid REFERENCES beacon (id) NOT NULL,
                                             person_id uuid REFERENCES person (id) NOT NULL
);

ALTER TABLE person
    DROP COLUMN person_type,
    DROP COLUMN beacon_id,
    ADD COLUMN account_id uuid REFERENCES beacon_account_holder (id);