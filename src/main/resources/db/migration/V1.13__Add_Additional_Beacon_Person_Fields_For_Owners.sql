-- Add additional columns required for the data migration
ALTER TABLE person
    ADD COLUMN alternative_telephone_number_2 text,
    ADD COLUMN telephone_number_2 text,
    ADD COLUMN last_modified_date text,
    ADD COLUMN country text,
    ADD COLUMN company_name text,
    ADD COLUMN care_of text,
    ADD COLUMN fax text,
    ADD COLUMN is_main text,
    ADD COLUMN create_user_id integer,
    ADD COLUMN update_user_id integer,
    ADD COLUMN versioning integer;

-- TODO: Remove once Zack's PR has gone in
ALTER TABLE person
    ALTER COLUMN beacon_id DROP NOT NULL;