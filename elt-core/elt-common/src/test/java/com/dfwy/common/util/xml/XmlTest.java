package com.dfwy.common.util.xml;

import com.dfwy.common.utils.xml.MapAdapter;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlTest {
    @SneakyThrows
    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("key1","value1");
        map.put("key2","value2");
        map.put("key3","value3");
        map.put("key4","value4");
        map.put("key5","value5");
        Map<String,String> map2 = new HashMap<>();
        map2.put("key1","value1");
        map2.put("key2","value2");
        map2.put("key3","value3");
        map2.put("key4","value4");
        map2.put("key5","value5");
        List<Map<String,String>> list= new ArrayList<>();
        list.add(map);
        list.add(map2);

        Test test  = new Test();
        test.setMap(map);
        test.setList(list);
        JAXBContext.newInstance(Test.class).createMarshaller().marshal(test, System.out);
    }


    @XmlRootElement(name="root")
    @Accessors(chain = true)
    @Data
    public static class Test{
        @XmlJavaTypeAdapter(MapAdapter.class)
        private Map<String, String> map = new HashMap<>();
        @XmlJavaTypeAdapter(MapAdapter.class)
        @XmlElementWrapper(name = "SBXX_LIST")
        @XmlElement(name="SBXX")

        private List<Map<String,String>> list= new ArrayList<>();
        @XmlTransient
        public List<Map<String, String>> getList() {
            return list;
        }



        @XmlTransient
        public Map<String, String> getMap() {
            return map;
        }


    }

}
