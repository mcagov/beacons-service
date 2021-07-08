package uk.gov.mca.beacons.api.jpa.entities;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import uk.gov.mca.beacons.api.domain.PersonType;

@Entity(name = "person")
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

  @Column(name = "telephone_number_2")
  private String telephoneNumber2;

  @Column(name = "alternative_telephone_number_2")
  private String alternativeTelephoneNumber2;

  private String email;

  @Enumerated(EnumType.STRING)
  private PersonType personType;

  private LocalDateTime createdDate;

  private LocalDateTime lastModifiedDate;

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

  private String country;

  private String companyName;

  private String careOf;

  private String fax;

  private String isMain;

  private Integer createUserId;

  private Integer updateUserId;

  private Integer versioning;

  private Boolean migrated;
}
