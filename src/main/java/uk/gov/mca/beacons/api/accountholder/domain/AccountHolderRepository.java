package uk.gov.mca.beacons.api.accountholder.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHolderRepository
  extends JpaRepository<AccountHolder, AccountHolderId> {
  Optional<AccountHolder> findAccountHolderByAuthId(String authId);
}
