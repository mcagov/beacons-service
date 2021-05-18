package uk.gov.mca.beacons.service.uses;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.service.mappers.ModelPatcher;
import uk.gov.mca.beacons.service.mappers.ModelPatcherFactory;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconUse;
import uk.gov.mca.beacons.service.repository.BeaconUseRepository;

@Service
public class BeaconUsesPatchService {

  private final BeaconUseRepository beaconUseRepository;
  private final ModelPatcherFactory<BeaconUse> beaconUsePatcherFactory;

  @Autowired
  public BeaconUsesPatchService(
    BeaconUseRepository beaconUseRepository,
    ModelPatcherFactory<BeaconUse> beaconUsePatcherFactory
  ) {
    this.beaconUseRepository = beaconUseRepository;
    this.beaconUsePatcherFactory = beaconUsePatcherFactory;
  }

  public void update(UUID id, BeaconUse beaconUseToUpdateWith) {
    final var currentBeaconUse = beaconUseRepository.findById(id);
    if (currentBeaconUse.isEmpty()) throw new ResourceNotFoundException();

    final var patcher = getBeaconUsePatcher();
    final var update = patcher.patchModel(
      currentBeaconUse.get(),
      beaconUseToUpdateWith
    );

    beaconUseRepository.save(update);
  }

  private ModelPatcher<BeaconUse> getBeaconUsePatcher() {
    return beaconUsePatcherFactory
      .getModelPatcher()
      .withMapping(BeaconUse::getEnvironment, BeaconUse::setEnvironment);
  }
}
