-- Add legacy_beacon_id column to note so that notes can also reference legacy beacons
ALTER TABLE note
    ADD COLUMN legacy_beacon_id UUID REFERENCES legacy_beacon (id) ON DELETE CASCADE; 
