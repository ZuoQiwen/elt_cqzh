package com.dfwy.socket.handler;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CCMSBODY")
public class SocketXml {
    private REQ_HEAD  REQ_HEAD;



    @Data
    @Accessors(chain = true)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "CCMSBODY")
    public static class REQ_HEAD{
        private String MSG_TYPE;
        private String TRAN_ASYNC;
        private String EXT_TRANCODE;
        private String SYS_ID;
        private String TRAN_DATE;
        private String TRAN_TIME;
        private String TRANCENO;
        private String AP_TRACENO;
        private String FILLER;
        private String DATE_LEN;


    }
}
