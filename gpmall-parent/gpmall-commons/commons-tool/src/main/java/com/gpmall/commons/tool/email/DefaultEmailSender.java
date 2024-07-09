package com.gpmall.commons.tool.email;


import org.springframework.stereotype.Component;

@Component
public class DefaultEmailSender extends AbstractEmailSender{
    @Override
    public void initProperties(MailData mailData) throws Exception {

    }

    @Override
    public void doSend(MailData mailData) throws Exception {

    }

    @Override
    public void doHtmlSend(MailData mailData) throws Exception {

    }

    @Override
    public void doSendWithAttachFile(MailData mailData) throws Exception {

    }

    @Override
    public void doHtmlSendWithUseTemplate(MailData mailData) throws Exception {

    }
}
