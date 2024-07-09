package com.gpmall.commons.tool.email;

import lombok.Data;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.*;

@Data
public class MailData {


    private List<String> receiveAddresses = Collections.emptyList();

    private List<String> ccAddresses = Collections.emptyList();


    private String subject;

    private String content;

    private Vector<String> attachFileNames = new Vector<>();

    private String contentType = "text/html; charset=utf-8";

    private String fileName;

    private Map<String, Object> dataMap;


    public Address[] getReceiverAddresses(){
        Address[] addresses = new Address[]{};
        List<InternetAddress> internetAddressList = new ArrayList<>();

        receiveAddresses.forEach(ra -> {
            try {
                internetAddressList.add(new InternetAddress(ra));
            } catch (AddressException ae){
                ae.printStackTrace();
            }
        });

        return internetAddressList.toArray(addresses);
    }



    public Address[] getCcAddresses(){
        Address[] addresses = new Address[]{};
        List<InternetAddress> internetAddressList = new ArrayList<>();

        ccAddresses.forEach(ra -> {
            try {
                internetAddressList.add(new InternetAddress(ra));
            } catch (AddressException ae){
                ae.printStackTrace();
            }
        });

        return internetAddressList.toArray(addresses);
    }


}
