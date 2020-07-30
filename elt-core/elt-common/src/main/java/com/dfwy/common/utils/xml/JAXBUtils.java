package com.dfwy.common.utils.xml;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Description:
 * date: 2020/3/9 19:09
 *
 * @author zuoqiwen
 */
@Slf4j
public class JAXBUtils{
    public static  String toXml(Object object,Class... classesToBeBound){
        try {
            JAXBContext context = JAXBContext.newInstance(classesToBeBound);
            Marshaller marshaller = context.createMarshaller();
            //格式化输出
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            //取消xml头部
            //marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            //取消特殊字符转译
            marshaller.setProperty("com.sun.xml.bind.characterEscapeHandler",new CharacterEscapeHandler() {
                @Override
                public void escape(char[] ch, int start, int length, boolean b, Writer writer) throws IOException {
                    writer.write(ch, start, length);
                }
            });
            StringWriter writer = new StringWriter();
            marshaller.marshal(object, writer);
            return  writer.toString();
        } catch (Exception e) {
            log.error("转换xml出错："+object,e);
            throw new RuntimeException("接口报文错误");
        }
    }
    public static<T>  T toObject(Class<T> clazz,String xml){
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            log.error("转换xml出错："+xml,e);
            throw new RuntimeException("接口报文错误");
        }
    }


}
