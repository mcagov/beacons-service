-- Tag Owners and Emergency Contacts with an account_holder_id

ALTER TABLE person
    ADD COLUMN account_holder_id UUID REFERENCES person (id);

UPDATE person
SET account_holder_id = account_holder.id
FROM account_holder,
     beacon
WHERE account_holder.id = beacon.account_holder_id
  AND person.beacon_id = beacon.id;