package uk.gov.mca.beacons.service.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WrapperDTO<T extends DomainDTO> {

  private final Map<String, Object> meta = new HashMap<String, Object>();
  private final List<T> data = new ArrayList<T>();
  private final List<DomainDTO> included = new ArrayList<DomainDTO>();

  public Map<String, Object> getMeta() {
    return meta;
  }

  public void addMeta(String key, String value) {
    meta.put(key, value);
  }

  public List<T> getData() {
    return data;
  }

  public void addData(T dataDTO) {
    data.add(dataDTO);
  }

  public List<DomainDTO> getIncluded() {
    return included;
  }

  public void addIncluded(DomainDTO includedDTO) {
    included.add(includedDTO);
  }
}
