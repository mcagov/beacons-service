package uk.gov.mca.beacons.service.dto;

import java.util.Map;

public class AccountHolderDTO extends DomainDTO<Map<String, Object>> {

  private String type = "accountHolder";

  public String getType() {
    return type;
  }
}
