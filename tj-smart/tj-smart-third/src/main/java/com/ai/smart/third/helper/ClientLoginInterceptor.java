package com.ai.smart.third.helper;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * cxf webservice 用户调用校验
 */
public class ClientLoginInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    private String UserName;
    private String UserPass;

    public ClientLoginInterceptor(String UserName, String UserPass){
        super(Phase.PREPARE_SEND);
        this.UserName = UserName;
        this.UserPass = UserPass;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }


    public String getUserPass() {
        return UserPass;
    }


    public void setUserPass(String userPass) {
        UserPass = userPass;
    }


    public void handleMessage(SoapMessage soap) throws Fault
    {
        List<Header> headers = soap.getHeaders();
        Document doc = DOMUtils.createDocument();
        Element auth = doc.createElementNS("http://tempuri.org/","SecurityHeader");
        Element UserName = doc.createElement("UserName");
        Element UserPass = doc.createElement("UserPass");

        UserName.setTextContent(this.UserName);
        UserPass.setTextContent(this.UserPass);

        auth.appendChild(UserName);
        auth.appendChild(UserPass);

        headers.add(0, new Header(new QName("SecurityHeader"),auth));
    }
}
