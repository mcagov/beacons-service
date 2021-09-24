package uk.gov.mca.beacons.api.services;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.domain.events.LegacyBeaconClaimEvent;
import uk.gov.mca.beacons.api.exceptions.BeaconsValidationException;
import uk.gov.mca.beacons.api.gateways.AccountHolderGateway;
import uk.gov.mca.beacons.api.gateways.EventGateway;
import uk.gov.mca.beacons.api.gateways.LegacyBeaconGateway;
import uk.gov.mca.beacons.api.mappers.LegacyBeaconMapper;
import uk.gov.mca.beacons.api.services.validation.LegacyBeaconValidator;

@Service
@Transactional
public class LegacyBeaconService {

    private final LegacyBeaconGateway legacyBeaconGateway;
    private final EventGateway eventGateway;
    private final AccountHolderGateway accountHolderGateway;
    private final LegacyBeaconValidator legacyBeaconValidator;
    private final LegacyBeaconMapper legacyBeaconMapper;
    private final CreateRegistrationService createRegistrationService;

    @Autowired
    public LegacyBeaconService(
            LegacyBeaconGateway legacyBeaconGateway,
            EventGateway eventGateway,
            AccountHolderGateway accountHolderGateway,
            LegacyBeaconValidator legacyBeaconValidator,
            LegacyBeaconMapper legacyBeaconMapper,
            CreateRegistrationService createRegistrationService
    ) {
        this.legacyBeaconGateway = legacyBeaconGateway;
        this.eventGateway = eventGateway;
        this.accountHolderGateway = accountHolderGateway;
        this.legacyBeaconValidator = legacyBeaconValidator;
        this.legacyBeaconMapper = legacyBeaconMapper;
        this.createRegistrationService = createRegistrationService;
    }

    public LegacyBeacon create(LegacyBeacon beacon) {
        validate(beacon);
        return legacyBeaconGateway.save(beacon);
    }

    private void validate(LegacyBeacon beacon) {
        final var errors = new BeanPropertyBindingResult(beacon, "legacyBeacon");
        legacyBeaconValidator.validate(beacon, errors);

        if (errors.hasFieldErrors()) {
            throw new BeaconsValidationException(errors);
        }
    }

    public void deleteAll() {
        legacyBeaconGateway.deleteAll();
    }

    public Optional<LegacyBeacon> findById(UUID id) {
        return legacyBeaconGateway.findById(id);
    }

    public void claim(LegacyBeacon legacyBeacon) {
        var accountHolder = accountHolderGateway.getByEmail(
                legacyBeacon.getAssociatedEmailAddress()
        );
        eventGateway.save(
                new LegacyBeaconClaimEvent(
                        legacyBeacon,
                        accountHolder,
                        OffsetDateTime.now()
                )
        );
        // Beacon beacon = legacyBeaconMapper.toBeacon(legacyBeacon);
        createRegistrationService.register(beacon);
    }
}
