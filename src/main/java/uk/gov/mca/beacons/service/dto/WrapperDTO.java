package uk.gov.mca.beacons.service.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WrapperDTO<T> {

    public Map<String, Object> meta;
    public ArrayList<T> data;

    public WrapperDTO() {
        meta = new HashMap<String, Object>();
        meta.putIfAbsent("count", 1);
        meta.putIfAbsent("pageSize", 1);

        data = new ArrayList<T>();
    }

    public void Add(T dataDTO){
        data.add(dataDTO);
    }
}