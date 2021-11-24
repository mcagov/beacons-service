package uk.gov.mca.beacons.api.accountholder.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHolderRepository
  extends JpaRepository<AccountHolder, AccountHolderId> {}
