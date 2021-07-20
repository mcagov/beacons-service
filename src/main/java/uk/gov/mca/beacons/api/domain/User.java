package uk.gov.mca.beacons.api.domain;

import java.util.UUID;

public interface User {
  UUID getId();
  String getFullName();
  String getEmail();
}
