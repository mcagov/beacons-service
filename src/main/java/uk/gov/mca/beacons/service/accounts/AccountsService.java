package uk.gov.mca.beacons.service.accounts;

import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.model.AccountHolder;

import java.util.UUID;

@Service
public class AccountsService {
    public UUID getId(String authId) {
        return UUID.randomUUID();
    }
}
