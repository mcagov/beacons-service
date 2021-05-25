package uk.gov.mca.beacons.service.hateoas;

public interface IHateoasLinkStrategy<T> {
  boolean checkGetPermission(T domain);

  String getGetPath(T domain);

  boolean checkPatchPermission(T domain);

  String getPatchPath(T domain);
}
