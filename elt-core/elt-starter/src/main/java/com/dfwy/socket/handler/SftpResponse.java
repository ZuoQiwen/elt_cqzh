package com.dfwy.socket.handler;

import com.dfwy.common.utils.xml.JAXBUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

public class SftpResponse {

    public static  String success(){
        return new Response("000000",null).toXml();
    }
    public static String fail(String msg){
        return new Response("000001",msg).toXml();
    }
    @Data
    @Accessors(chain = true)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "CCMSBODY")
    public static class Response{
        private String RT_CODE;
        private String RT_MSG;

        public Response(String RT_CODE,String RT_MSG){
            this.RT_CODE = RT_CODE;
            this.RT_MSG = RT_MSG;
        }

        public  String toXml(){
            return JAXBUtils.toXml(this, Response.class);
        }
    }

}
