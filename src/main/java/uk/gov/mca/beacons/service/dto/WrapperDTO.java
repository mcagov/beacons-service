package uk.gov.mca.beacons.service.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WrapperDTO<T> {

  public Map<String, Object> getMeta() {
    return meta;
  }

  public ArrayList<T> getData() {
    return data;
  }

  private Map<String, Object> meta;
  private ArrayList<T> data;

  public WrapperDTO() {
    meta = new HashMap<String, Object>();
    data = new ArrayList<T>();
  }

  public void AddMeta(String key, String value) {
    meta.put(key, value);
  }

  public void AddData(T dataDTO) {
    data.add(dataDTO);
  }
}
