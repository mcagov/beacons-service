package uk.gov.mca.beacons.service.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "beacon_account_holder")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class AccountHolder {

  @Id
  @GeneratedValue
  private UUID id;

  private String authId;

  private String email;

  private String fullName;

  private String telephoneNumber;

  private String alternativeTelephoneNumber;

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
