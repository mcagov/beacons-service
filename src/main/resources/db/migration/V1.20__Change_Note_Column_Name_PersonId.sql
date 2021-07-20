-- Change column name, personId to userAuthId feels better than note.note
ALTER TABLE note
    RENAME COLUMN person_id TO user_id;