package uk.gov.mca.beacons.service.beacon;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "beacons")
public class Beacon {

  @Id
  @GeneratedValue
  @Column
  UUID id;

  @Override
  public String toString() {
    return id.toString();
  }
}
