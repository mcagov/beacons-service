package uk.gov.mca.beacons.service.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class WrapperDTO<T extends DomainDTO> {

  private Map<String, Object> meta;
  private ArrayList<T> data;
  private ArrayList<DomainDTO> included;

  public WrapperDTO() {
    meta = new HashMap<String, Object>();
    data = new ArrayList<T>();
    included = new ArrayList<DomainDTO>();
  }

  public Map<String, Object> getMeta() {
    return meta;
  }

  public void AddMeta(String key, String value) {
    meta.put(key, value);
  }

  public ArrayList<T> getData() {
    return data;
  }

  public void AddData(T dataDTO) {
    data.add(dataDTO);
  }

  public ArrayList<DomainDTO> getIncluded() {
    return included;
  }

  public void AddIncluded(DomainDTO includedDTO) {
    included.add(includedDTO);
  }
}
