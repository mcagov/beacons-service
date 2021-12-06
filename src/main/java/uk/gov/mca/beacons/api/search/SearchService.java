package uk.gov.mca.beacons.api.search;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.accountholder.domain.AccountHolder;
import uk.gov.mca.beacons.api.accountholder.domain.AccountHolderId;
import uk.gov.mca.beacons.api.accountholder.domain.AccountHolderRepository;
import uk.gov.mca.beacons.api.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.api.search.documents.AccountHolderDocument;
import uk.gov.mca.beacons.api.search.repositories.AccountHolderSearchRepository;

@Service("Opensearch service")
public class SearchService {

  private final AccountHolderRepository accountHolderRepository;
  private final AccountHolderSearchRepository accountHolderSearchRepository;

  @Autowired
  public SearchService(
    AccountHolderRepository accountHolderRepository,
    AccountHolderSearchRepository accountHolderSearchRepository
  ) {
    this.accountHolderRepository = accountHolderRepository;
    this.accountHolderSearchRepository = accountHolderSearchRepository;
  }

  public void updateSearch(AccountHolderId accountHolderId) {
    AccountHolder accountHolder = accountHolderRepository
      .findById(accountHolderId)
      .orElseThrow(ResourceNotFoundException::new);

    AccountHolderDocument accountHolderDocument = new AccountHolderDocument();
    accountHolderDocument.setId(
      Objects.requireNonNull(accountHolder.getId()).unwrap()
    );
    accountHolderDocument.setName(accountHolder.getFullName());
    accountHolderDocument.setEmail(accountHolder.getEmail());
    accountHolderDocument.setLastModified(accountHolder.getLastModifiedDate());
    accountHolderDocument.setCreatedAt(accountHolder.getCreatedDate());

    accountHolderSearchRepository.save(accountHolderDocument);
  }
}
