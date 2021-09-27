CREATE TYPE claim_event_type AS ENUM ('claim', 'reject');

CREATE TABLE IF NOT EXISTS claim_event
(
    id                UUID PRIMARY KEY,
    type              claim_event_type                    NOT NULL,
    legacy_beacon_id  UUID REFERENCES legacy_beacon (id)  NOT NULL,
    account_holder_id UUID REFERENCES account_holder (id) NOT NULL,
    date_time         TIMESTAMP WITH TIME ZONE            NOT NULL,
    reason            VARCHAR(500)
)