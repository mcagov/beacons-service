-- Adds missing beacon use and person columns

ALTER TABLE beacon_use
    ADD COLUMN port_letter_number text,
    ADD COLUMN other_environment text;

ALTER TABLE beacon_person
    RENAME COLUMN country TO county;

ALTER TABLE beacon_person
    RENAME COLUMN telephone TO telephone_number;

ALTER TABLE beacon_person
    ADD COLUMN alternative_telephone_number text,
    ADD COLUMN town_or_city text;