package com.gpmall.commons.tool.email;

public interface EmailSender {


    void sendMail(MailData mailData) throws Exception;



    void sendMailWithAttachFile(MailData mailData) throws Exception;



    void sendHtmlMail(MailData mailData) throws Exception;




    void sendHtmlMailWithUseTemplate(MailData mailData) throws Exception;


}
