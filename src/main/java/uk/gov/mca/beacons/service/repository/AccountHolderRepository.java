package uk.gov.mca.beacons.service.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.service.model.AccountHolder;

@Repository
public interface AccountHolderRepository
        extends JpaRepository<AccountHolder, UUID> {
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM account_holder WHERE auth_id = ?1"
    )
    AccountHolder getByAuthId(String authId);
}
