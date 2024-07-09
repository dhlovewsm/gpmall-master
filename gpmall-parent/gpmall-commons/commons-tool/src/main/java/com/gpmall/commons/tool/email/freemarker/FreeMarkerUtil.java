package com.gpmall.commons.tool.email.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class FreeMarkerUtil {

    public static String MailTextForTemplate(String templatePath, String fileName, Map datas) throws IOException, TemplateException {

        Configuration configuration = new Configuration();
        configuration.setDirectoryForTemplateLoading(new File(FreeMarkerUtil.class.getClass().getResource("/" + templatePath).getPath()));
        Template template = configuration.getTemplate(fileName, "utf-8");
        StringWriter out = new StringWriter();
        template.process(datas, out);
        return out.toString();
    }

}
