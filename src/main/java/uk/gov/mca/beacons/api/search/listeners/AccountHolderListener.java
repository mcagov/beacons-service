package uk.gov.mca.beacons.api.search.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import uk.gov.mca.beacons.api.accountholder.domain.events.AccountHolderUpdated;
import uk.gov.mca.beacons.api.search.SearchService;

@Component
public class AccountHolderListener {

  private final SearchService searchService;

  @Autowired
  public AccountHolderListener(SearchService searchService) {
    this.searchService = searchService;
  }

  @EventListener
  public void onAccountHolderUpdate(AccountHolderUpdated event) {
    searchService.updateSearch(event.getAccountHolderId());
  }
}
