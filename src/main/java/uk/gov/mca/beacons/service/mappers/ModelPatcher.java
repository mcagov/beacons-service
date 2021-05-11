package uk.gov.mca.beacons.service.mappers;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class ModelPatcher<T> {

  private T model;
  private T update;

  public ModelPatcher(T model, T update) {
    super();
    this.model = model;
    this.update = update;
  }

  public <TValue> void patchModel(
    Function<T, TValue> get,
    BiConsumer<T, TValue> set
  ) {
    TValue updateValue = get.apply(update);
    if (updateValue == null) return;

    set.accept(model, updateValue);
  }
}
