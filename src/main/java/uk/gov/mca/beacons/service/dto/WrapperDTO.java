package uk.gov.mca.beacons.service.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WrapperDTO<T extends DomainDTO> {

  private final Map<String, Object> meta = new HashMap<String, Object>();
  private final ArrayList<T> data = new ArrayList<T>();
  private final ArrayList<DomainDTO> included = new ArrayList<DomainDTO>();

  public Map<String, Object> getMeta() {
    return meta;
  }

  public void addMeta(String key, String value) {
    meta.put(key, value);
  }

  public ArrayList<T> getData() {
    return data;
  }

  public void addData(T dataDTO) {
    data.add(dataDTO);
  }

  public ArrayList<DomainDTO> getIncluded() {
    return included;
  }

  public void addIncluded(DomainDTO includedDTO) {
    included.add(includedDTO);
  }
}
