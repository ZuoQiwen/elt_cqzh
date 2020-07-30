package com.dfwy.common.utils.api;

import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
    @SneakyThrows
    public  static String add(String date, int i, int j){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(date));
        calendar.add(i, j);
        return  sdf.format(calendar.getTime());
    }
}
