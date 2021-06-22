-- Add additional columns required for the data migration
ALTER TABLE person
    ADD COLUMN last_modified_date text,
    ADD COLUMN country text,
    ADD COLUMN company_name text,
    ADD COLUMN care_of text,
    ADD COLUMN fax text,
    ADD COLUMN is_main text,
    ADD COLUMN create_user_id integer,
    ADD COLUMN update_user_id integer,
    ADD COLUMN versioning interval;