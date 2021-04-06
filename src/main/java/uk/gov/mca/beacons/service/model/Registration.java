package uk.gov.mca.beacons.service.model;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Registration {

  @Id
  private UUID id;

  public UUID getId() {
    return UUID.randomUUID();
  }
}
