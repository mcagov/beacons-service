package uk.gov.mca.beacons.service.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BeaconDTO {

    private String type = "beacon";
    private UUID id;
    private Map<String, String> attributes = new HashMap<String, String>();

    public String getType() {
        return type;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    // public void setAttributes(Map<String, String> attributes) {
    //     this.attributes = attributes;
    // }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }



}
