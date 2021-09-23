-- Create a claim_event table for persisting LegacyBeaconClaimEvent objects
CREATE TYPE claim AS ENUM ('claim', 'reject');

CREATE TABLE IF NOT EXISTS claim_event
(
    id                 uuid PRIMARY KEY,
    type               claim                               NOT NULL,
    legacy_beacon_id   uuid REFERENCES legacy_beacon (id)  NOT NULL,
    account_holder_id  uuid REFERENCES account_holder (id) NOT NULL,
    date_time          TIMESTAMP WITH TIME ZONE            NOT NULL,
    reason             varchar(500)
)
