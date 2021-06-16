package uk.gov.mca.beacons.service.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WrapperDTO<T> {

  private final Map<String, Object> meta = new HashMap<String, Object>();
  private T data = null;
  private final List<DomainDTO> included = new ArrayList<DomainDTO>();

  public Map<String, Object> getMeta() {
    return meta;
  }

  public void addMeta(String key, String value) {
    meta.put(key, value);
  }

  public T getData() {
    return data;
  }

  public void setData(T dataDTO) {
    this.data = dataDTO;
  }

  public List<DomainDTO> getIncluded() {
    return included;
  }

  public void addIncluded(DomainDTO includedDTO) {
    included.add(includedDTO);
  }
}
