package com.dfwy.common.utils.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Description: 添加CDATA属性字段，自定义实现adapter
 * date: 2020/3/17 16:04
 *
 * @author zuoqiwen
 */
public class CDATAAdapter extends XmlAdapter<String,String> {
    @Override
    public String unmarshal(String v) throws Exception {
        return v;
    }

    @Override
    public String marshal(String v) throws Exception {
        return "<![CDATA[" + v + "]]>";
    }
}
