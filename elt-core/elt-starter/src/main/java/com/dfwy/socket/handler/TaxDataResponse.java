package com.dfwy.socket.handler;

import com.dfwy.common.utils.xml.JAXBUtils;
import com.dfwy.common.utils.xml.MapAdapter;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.CollectionUtils;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaxDataResponse {
    public static String failed(String message) {
        return new Response("00000", null, null).toXml();
    }


    public static String success(Map<String, List<Map<String, String>>> data) {
        return new Response("90000", null, data).toXml();
    }


    @Data
    @Accessors(chain = true)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "REP_MSG")
    public static class Response {
        @XmlElement(name = "Result")
        private String result;
        @XmlElement(name = "INF")
        public String inf;
        @XmlElement(name = "RM")
        public Data rm;
        public Response(){}
        public Response(String result, String inf, Map<String, List<Map<String, String>>> data) {
            this.result = result;
            this.inf = inf;
            this.rm = new Data(data);
        }

        public String toXml() {
            return JAXBUtils.toXml(this, Response.class);
        }

        @lombok.Data
        @Accessors(chain = true)
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlRootElement(name = "RM")
        public static class Data {
            @XmlJavaTypeAdapter(MapAdapter.class)
            private Map<String, String> NSR;

            @XmlJavaTypeAdapter(MapAdapter.class)
            private Map<String, String> JCXX;

            @XmlJavaTypeAdapter(MapAdapter.class)
            private Map<String, String> FRXX;

            @XmlJavaTypeAdapter(MapAdapter.class)
            private Map<String, String> XYJB;

            //10
            private int TZF_COUNT;

            @XmlJavaTypeAdapter(MapAdapter.class)
            private List<Map<String, String>> TZF;

            @XmlJavaTypeAdapter(MapAdapter.class)
            private Map<String, String> KZXX;

            @XmlJavaTypeAdapter(MapAdapter.class)
            private Map<String, String> PZQK;

            //10
            private int QYBGXX_COUNT;

            @XmlJavaTypeAdapter(MapAdapter.class)
            private List<Map<String, String>> QYBGXX;

            //400
            private int YBNSRSBZB_COUNT;

            @XmlJavaTypeAdapter(MapAdapter.class)
            private List<Map<String, String>> YBNSRSBZB;


            //40
            private int SBMXZB_COUNT;
            @XmlJavaTypeAdapter(MapAdapter.class)
            private List<Map<String, String>> SBMXZB;

            //40
            private int GTHSB_COUNT;
            @XmlJavaTypeAdapter(MapAdapter.class)
            private List<Map<String, String>> GTHSB;

            //400
            private int QYZSXX_COUNT;
            @XmlJavaTypeAdapter(MapAdapter.class)
            private List<Map<String, String>> QYZSXX;

            //400
            private int QYSYSJ_COUNT;
            @XmlJavaTypeAdapter(MapAdapter.class)
            private List<Map<String, String>> QYSYSJ;

            //400
            private int QYXYSJ_COUNT;
            @XmlJavaTypeAdapter(MapAdapter.class)
            private List<Map<String, String>> QYXYSJ;

            //40*73
            private int ZCFZB_COUNT;
            @XmlJavaTypeAdapter(MapAdapter.class)
            private List<Map<String, String>> ZCFZB;

            //40*32
            private int QYLRB_COUNT;
            @XmlJavaTypeAdapter(MapAdapter.class)
            private List<Map<String, String>> QYLRB;
            //10
            private int QYJHAJ_COUNT;
            @XmlJavaTypeAdapter(MapAdapter.class)
            private List<Map<String, String>> QYJHAJ;


            private int RES01_COUNT;
            @XmlJavaTypeAdapter(MapAdapter.class)
            private List<Map<String, String>> RES01;
            private int RES02_COUNT;
            @XmlJavaTypeAdapter(MapAdapter.class)
            private List<Map<String, String>> RES02;
            private int RES03_COUNT;
            @XmlJavaTypeAdapter(MapAdapter.class)
            private List<Map<String, String>> RES03;
            private int RES04_COUNT;
            @XmlJavaTypeAdapter(MapAdapter.class)
            private List<Map<String, String>> RES04;
            private int RES05_COUNT;
            @XmlJavaTypeAdapter(MapAdapter.class)
            private List<Map<String, String>> RES05;
            @XmlJavaTypeAdapter(MapAdapter.class)
            @XmlElementWrapper
            private List<Map<String, String>> RES99;

            public Data(){

            }
            public Data(Map<String, List<Map<String, String>>> data) {
                List<Map<String, String>> empty = new ArrayList<>();
                this.NSR = !CollectionUtils.isEmpty(data.get("NSR")) ? data.get("NSR").get(0) : new HashMap<>();
                this.JCXX = !CollectionUtils.isEmpty(data.get("JCXX")) ? data.get("JCXX").get(0) : new HashMap<>();
                this.FRXX = !CollectionUtils.isEmpty(data.get("FRXX")) ? data.get("FRXX").get(0) : new HashMap<>();
                this.XYJB = !CollectionUtils.isEmpty(data.get("XYJB")) ? data.get("XYJB").get(0) : new HashMap<>();
                this.TZF_COUNT = CollectionUtils.isEmpty(data.get("TZF")) ? 0 : data.get("TZF").size();
                this.TZF = data.getOrDefault("TZF", empty);

                this.KZXX = !CollectionUtils.isEmpty(data.get("KZXX")) ? data.get("KZXX").get(0) : new HashMap<>();
                this.PZQK = !CollectionUtils.isEmpty(data.get("PZQK")) ? data.get("PZQK").get(0) : new HashMap<>();

                this.QYBGXX_COUNT = CollectionUtils.isEmpty(data.get("QYBGXX")) ? 0 : data.get("QYBGXX").size();
                this.QYBGXX = data.getOrDefault("QYBGXX", empty);


                this.YBNSRSBZB_COUNT = CollectionUtils.isEmpty(data.get("YBNSRSBZB")) ? 0 : data.get("YBNSRSBZB").size();
                this.YBNSRSBZB = data.getOrDefault("YBNSRSBZB", empty);

                this.SBMXZB_COUNT = CollectionUtils.isEmpty(data.get("SBMXZB")) ? 0 : data.get("SBMXZB").size();
                this.SBMXZB = data.getOrDefault("SBMXZB", empty);

                this.QYZSXX_COUNT = CollectionUtils.isEmpty(data.get("QYZSXX")) ? 0 : data.get("QYZSXX").size();
                this.QYZSXX = data.getOrDefault("QYZSXX", empty);

                this.QYSYSJ_COUNT = CollectionUtils.isEmpty(data.get("QYSYSJ")) ? 0 : data.get("QYSYSJ").size();
                this.QYSYSJ = data.getOrDefault("QYSYSJ", empty);

                this.QYXYSJ_COUNT = CollectionUtils.isEmpty(data.get("QYXYSJ")) ? 0 : data.get("QYXYSJ").size();
                this.QYXYSJ = data.getOrDefault("QYXYSJ", empty);


                this.ZCFZB_COUNT = CollectionUtils.isEmpty(data.get("ZCFZB")) ? 0 : data.get("ZCFZB").size();
                this.ZCFZB = data.getOrDefault("ZCFZB", empty);

                this.QYLRB_COUNT = CollectionUtils.isEmpty(data.get("QYLRB")) ? 0 : data.get("QYLRB").size();
                this.QYLRB = data.getOrDefault("QYLRB", empty);

                this.QYJHAJ_COUNT = CollectionUtils.isEmpty(data.get("QYJHAJ")) ? 0 : data.get("QYJHAJ").size();
                this.QYJHAJ = data.getOrDefault("QYJHAJ", empty);

                this.RES01_COUNT = 0;
                this.RES01 = empty;
                this.RES02_COUNT = 0;
                this.RES02 = empty;
                this.RES03_COUNT = 0;
                this.RES03 = empty;
                this.RES04_COUNT = 0;
                this.RES04 = empty;
                this.RES05_COUNT = 0;
                this.RES05 = empty;
            }

        }
    }
}
