package com.dfwy;

import com.dfwy.service.CQHelper;

import java.io.IOException;

public class TaxDataTest {

    public static void main(String[] args) throws IOException {
        System.out.println(new CQHelper().post("http://10.116.166.12:7080/restOriginal?ServiceID=09001000000001&SourceSysID=09001","12","{\"a\":1}"));;
    }
}
