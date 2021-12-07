package uk.gov.mca.beacons.api.search.documents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;

public class BeaconDocument {

  @JsonIgnore
  UUID id;

  String hexId;
  String model;
  String manufacturer;
}
