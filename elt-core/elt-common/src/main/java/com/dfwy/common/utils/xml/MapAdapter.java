package com.dfwy.common.utils.xml;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.namespace.QName;
import java.util.Map;

public class MapAdapter extends XmlAdapter<MapWrapper, Map<String, String>> {
    @Override
    public Map<String, String> unmarshal(MapWrapper v) throws Exception {
        Map<String, String> map = v.toMap();
        return map;
    }

    @Override
    public MapWrapper marshal(Map<String, String> v) throws Exception {
        MapWrapper wrapper = new MapWrapper();
        for(Map.Entry<String, String> entry : v.entrySet()){
            wrapper.addEntry(new JAXBElement<String>(new QName(entry.getKey()), String.class, entry.getValue()));
        }
        return wrapper;
    }
}
