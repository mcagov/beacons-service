package uk.gov.mca.beacons.service.beacon;

import java.util.UUID;
import javax.persistence.*;

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
