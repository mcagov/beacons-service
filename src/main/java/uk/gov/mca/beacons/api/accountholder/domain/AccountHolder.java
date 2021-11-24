package uk.gov.mca.beacons.api.accountholder.domain;

import java.time.OffsetDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uk.gov.mca.beacons.api.shared.domain.base.BaseAggregateRoot;
import uk.gov.mca.beacons.api.shared.domain.person.Address;

@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "account_holder")
public class AccountHolder extends BaseAggregateRoot<AccountHolderId> {

  public static final String ID_GENERATOR_NAME = "accountholder-id-generator";

  @Type(type = "uk.gov.mca.beacons.api.accountholder.domain.AccountHolderId")
  @Column(nullable = false)
  @Id
  @GeneratedValue(
    strategy = javax.persistence.GenerationType.AUTO,
    generator = "accountholder-id-generator"
  )
  AccountHolderId id;

  @Setter
  private String fullName;

  @Setter
  @NotNull
  private String email;

  @Setter
  private String telephoneNumber;

  @Setter
  private String alternativeTelephoneNumber;

  @Embedded
  @Setter
  @NotNull(message = "Address must not be null")
  private Address address;

  @CreatedDate
  private OffsetDateTime createdDate;

  @LastModifiedDate
  private OffsetDateTime lastModifiedDate;
}
