package uk.gov.mca.beacons.service.mappers;

import org.springframework.stereotype.Service;

@Service
public class ModelPatcherFactory<T> {

  public ModelPatcherFactory() {
    super();
  }

  public ModelPatcher<T> getModelPatcher() {
    return new ModelPatcher<T>();
  }
}
