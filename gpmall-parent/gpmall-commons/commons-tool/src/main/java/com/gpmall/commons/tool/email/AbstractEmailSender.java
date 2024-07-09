package com.gpmall.commons.tool.email;

import com.gpmall.commons.tool.exception.BizException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractEmailSender implements EmailSender{


    @Override
    public void sendMail(MailData mailData) throws Exception {
        initProperties(mailData);
        validatorMailData(mailData);
        doSend(mailData);
    }


    @Override
    public void sendMailWithAttachFile(MailData mailData) throws Exception {
        initProperties(mailData);
        validatorMailData(mailData);
        validatorMailDataAttachFile(mailData);
        doSendWithAttachFile(mailData);
    }


    @Override
    public void sendHtmlMail(MailData mailData) throws Exception {
        initProperties(mailData);
        validatorMailData(mailData);
        doHtmlSend(mailData);
    }


    @Override
    public void sendHtmlMailWithUseTemplate(MailData mailData) throws Exception {
        initProperties(mailData);
        validatorMailData(mailData);
        doHtmlSendWithUseTemplate(mailData);
    }



    public abstract void initProperties(MailData mailData) throws Exception;


    public abstract void doSend(MailData mailData) throws Exception;


    public abstract void doHtmlSend(MailData mailData) throws Exception;


    public abstract void doSendWithAttachFile(MailData mailData) throws Exception;


    public abstract void doHtmlSendWithUseTemplate(MailData mailData) throws Exception;



    public void validatorMailData(MailData mailData){

        if (mailData.getReceiverAddresses() == null || mailData.getReceiveAddresses().isEmpty()){
            throw new BizException("error", "邮件发送地址不能为空");
        }

        if (!StringUtils.isNoneBlank(mailData.getSubject())){
            throw new BizException("error", "邮件主题不能为空");
        }

        if (!StringUtils.isNoneBlank(mailData.getContent())){
            throw new BizException("error", "邮件内容不能为空");
        }

    }


    public void validatorMailDataAttachFile(MailData mailData){
        if (ArrayUtils.isEmpty(mailData.getAttachFileNames().toArray())){
            throw new BizException("error", "邮件附件不能为空");
        }
    }

}
