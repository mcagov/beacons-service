package uk.gov.mca.beacons.api.search.listeners;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import uk.gov.mca.beacons.api.accountholder.domain.events.AccountHolderCreated;
import uk.gov.mca.beacons.api.accountholder.domain.events.AccountHolderUpdated;
import uk.gov.mca.beacons.api.search.SearchService;

@Component
public class AccountHolderListener {

  private final SearchService searchService;

  @Autowired
  public AccountHolderListener(SearchService searchService) {
    this.searchService = searchService;
  }

  @TransactionalEventListener
  public void onAccountHolderUpdate(AccountHolderUpdated event) {
    searchService.updateSearch(event.getAccountHolderId());
  }

  @TransactionalEventListener
  public void onAccountHolderCreated(AccountHolderCreated event) {
    searchService.updateSearch(event.getAccountHolderId());
  }
}
