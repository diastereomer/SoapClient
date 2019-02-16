/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soapclient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.apache.axiom.om.*;

/**
 *
 * @author PWang
 */
public class SoapClient {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        // Send SOAP Message to SOAP Server
        String url = "http://localhost:8040/services/integration";
        
        String message="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
"    <soapenv:Header>\n" +
"        <wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"\n" +
"                       xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"\n" +
"                       xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
"            <wsse:UsernameToken wsu:Id=\"UsernameToken-1\">\n" +
"                <wsse:Username>john</wsse:Username>\n" +
"                <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">doe</wsse:Password>\n" +
"            </wsse:UsernameToken>\n" +
"        </wsse:Security>\n" +
"    </soapenv:Header>\n" +
"    <soapenv:Body>\n" +
"      <processRequest xmlns=\"http://integration.peitron.com/\">\n" +
"         <SubmissionUpdate xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"SubmissionUpdate.xsd\">\n" +
"         </SubmissionUpdate>\n" +
"      </processRequest>\n" +
"    </soapenv:Body>\n" +
"</soapenv:Envelope>";
        InputStream is = new ByteArrayInputStream(message.getBytes());
       
        SOAPMessage request = MessageFactory.newInstance().createMessage(null, is);
        SOAPMessage soapResponse = soapConnection.call(request, url);

        // print SOAP Response
        System.out.print("Response SOAP Message:");
        soapResponse.writeTo(System.out);

        soapConnection.close();
    }

    private static SOAPMessage createSOAPRequest() throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.setPrefix("env");
        SOAPHeader header= envelope.getHeader();
        
                SOAPElement security =
                        header.addChildElement("Security", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
                security.addAttribute(new QName("env:mustUnderstand"), "1");
                security.addAttribute(new QName("xmlns:wsu"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
                security.addAttribute(new QName("wsu:Id"), "http://www.w3.org/2005/08/addressing");
               
                SOAPElement usernameToken =
                        security.addChildElement("UsernameToken", "wsse");
                usernameToken.addAttribute(new QName("wsu:Id"), "UsernameToken-2EFD3717A509DC18FF149002546498534");
 
                SOAPElement username =
                        usernameToken.addChildElement("Username", "wsse");
                username.addTextNode("username");
 
                SOAPElement password =
                        usernameToken.addChildElement("Password", "wsse");
                password.setAttribute("Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
            	password.addTextNode("password");
        
        SOAPBody body=envelope.getBody();
        body.appendChild();
        
        return soapMessage;
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
