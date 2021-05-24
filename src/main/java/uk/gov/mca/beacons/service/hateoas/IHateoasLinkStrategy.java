package uk.gov.mca.beacons.service.hateoas;

import org.springframework.stereotype.Service;

@Service
public interface IHateoasLinkStrategy<T> {
  public boolean checkGetPermission(T domain);

  public String getGetPath(T domain);

  public boolean checkPatchPermission(T domain);

  public String getPatchPath(T domain);
}
