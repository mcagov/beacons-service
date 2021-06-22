package uk.gov.mca.beacons.api.db;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uk.gov.mca.beacons.api.entities.PersonType;

@Entity(name = "person")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Person {

  @Id
  @GeneratedValue
  private UUID id;

  private UUID beaconId;

  private String fullName;

  private String telephoneNumber;

  private String alternativeTelephoneNumber;

  @Email
  private String email;

  @Enumerated(EnumType.STRING)
  private PersonType personType;

  @CreatedDate
  private LocalDateTime createdDate;

  @Column(name = "address_line_1")
  private String addressLine1;

  @Column(name = "address_line_2")
  private String addressLine2;

  @Column(name = "address_line_3")
  private String addressLine3;

  @Column(name = "address_line_4")
  private String addressLine4;

  private String townOrCity;

  private String postcode;

  private String county;
}
