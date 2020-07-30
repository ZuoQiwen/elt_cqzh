package com.dfwy.builder.sort;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortTest {

    @Test
    public void sortRule(){
//        System.out.println(new SortHelper().init("FILED1 DESC,FIELD2 ASC,FIELD3 DESC"));
//        System.out.println(new SortHelper().init("FILED1,FIELD2,FIELD3 DESC"));
    }


    @Test
    public void sort(){
        String rule = "A DESC,B ASC,C DESC";
        List<Map<String,String>> list =  new ArrayList<>();
        Map<String,String> map1 = new HashMap<>();
        map1.put("A","1");
        map1.put("B","3");
        map1.put("C","4");
        map1.put("D","5");
        Map<String,String> map2 = new HashMap<>();
        map2.put("A","1");
        map2.put("B","1");
        map2.put("C","1");
        map2.put("D","1");
        Map<String,String> map3 = new HashMap<>();
        map3.put("A","11");
        map3.put("B","1");
        map3.put("C","21");
        map3.put("D","1");
        list.add(map1);
        list.add(map2);
        list.add(map3);

        System.out.println(list);
        new SortHelper().sort(list,rule);
        System.out.println(list);
    }
}
