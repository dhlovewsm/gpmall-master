package com.gpmall.commons.tool.email.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

@Data
@Component
@ConfigurationProperties(prefix = "email")
public class EmailConfig {


    private String mailServerHost;

    private String mailServerPort;

    private String  fromAddress;

    private List<String> receiverAddresses;

    private List<String> ccAddresses;

    private String username;

    private String password;

    private boolean mailSmtpAuth = false;

    private String subject;

    private String content;

    private Vector<String> attachFileNames;

    private String mailDebug = "false";

    private String contentType = "text/html; charset=utf-8";

    private String templatePath = "emailTemplate";

    private String userMailActiveUrl;


    public Properties getProperties() {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", this.mailServerHost);
        properties.put("mail.smtp.port", this.mailServerPort);
        properties.put("mail.smtp.auth", this.mailSmtpAuth);
        return properties;
    }


    public Address[] getReceiverAddresses() {

        Address[] addresses = new Address[]{};
        List<InternetAddress> internetAddressList = new ArrayList<>();
        receiverAddresses.forEach( ra -> {
            try {
                internetAddressList.add(new InternetAddress(ra));
            } catch (AddressException ae){
                ae.printStackTrace();
            }
        });
        return internetAddressList.toArray(addresses);
    }


    public Address[] getCcAddresses() {

        Address[] addresses = new Address[]{};
        List<InternetAddress> internetAddressList = new ArrayList<>();
        ccAddresses.forEach( ra -> {
            try {
                internetAddressList.add(new InternetAddress(ra));
            } catch (AddressException ae){
                ae.printStackTrace();
            }
        });
        return internetAddressList.toArray(addresses);
    }


}
