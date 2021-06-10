package uk.gov.mca.beacons.service.accounts;

import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.model.AccountHolder;

import javax.transaction.Transactional;

@Service
@Transactional
public class CreateAccountHolderService {
    public void execute(AccountHolder accountHolder) {
    }
}