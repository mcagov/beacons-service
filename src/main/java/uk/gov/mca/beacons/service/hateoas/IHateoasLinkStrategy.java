package uk.gov.mca.beacons.service.hateoas;

public interface IHateoasLinkStrategy<T> {
  boolean userCanGetEntity(T domain);

  String getGetPath(T domain);

  boolean userCanPatchEntity(T domain);

  String getPatchPath(T domain);
}
