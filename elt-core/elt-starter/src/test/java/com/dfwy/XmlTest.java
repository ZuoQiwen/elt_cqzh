package com.dfwy;

import com.dfwy.builder.config.ELTConfiger;
import com.dfwy.common.domain.CQBOCData;
import com.dfwy.common.utils.Result;
import com.dfwy.common.utils.xml.JAXBUtils;
import com.dfwy.common.utils.xml.MapAdapter;
import com.dfwy.service.impl.TaxDataServiceImpl;
import com.dfwy.socket.handler.TaxDataResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
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
        //test.setList(list);
        System.out.println(JAXBUtils.toXml(test,Test.class));;

    }

//    @org.junit.Test
//    public void test(){
//        TaxDataServiceImpl taxDataService = new TaxDataServiceImpl();
//        taxDataService.setStatus(0);
//        ELTConfiger eltConfiger = new ELTConfiger();
//        eltConfiger.setYsbBwjdExchageMap(new HashMap<>());
//        taxDataService.setEltConfiger(eltConfiger);
//        CQBOCData cqbocData = new CQBOCData().setNSRSBH("123");
//        System.out.println(TaxDataResponse.success(taxDataService.format(taxDataService.taxData(cqbocData))));
//     }

    /**
     *
     * <dependency>
     *             <groupId>org.simpleframework</groupId>
     *             <artifactId>simple-xml</artifactId>
     *             <version>2.7.1</version>
     *         </dependency>
     */
//    @org.junit.Test
//    public void test1() throws JsonProcessingException {
//
//         TaxDataServiceImpl taxDataService = new TaxDataServiceImpl();
//         taxDataService.setStatus(0);
//         ELTConfiger eltConfiger = new ELTConfiger();
//         eltConfiger.setYsbBwjdExchageMap(new HashMap<>());
//         taxDataService.setEltConfiger(eltConfiger);
//         CQBOCData cqbocData = new CQBOCData().setNSRSBH("123");
//        ObjectMapper xmlMapper = new XmlMapper();
//        String xml = xmlMapper.writeValueAsString(new TaxDataResponse.Response("90000", null, (taxDataService.format(taxDataService.taxData(cqbocData)))));
//        System.out.println(xml);
//    }
    @XmlRootElement(name="root")
    @Accessors(chain = true)
    @Data
    public static class Test{
        @XmlJavaTypeAdapter(MapAdapter.class)
        private Map<String, String> map = new HashMap<>();
        @XmlJavaTypeAdapter(MapAdapter.class)
        @XmlElementWrapper
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
