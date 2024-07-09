package com.gpmall.commons.tool.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class DateUtils {


    public static final String DATE_LONG = "yyyyMMddHHmmss";

    public static final String DATE_SIMPLE = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_SHORT = "yyyyMMdd";


    public static String getOrderNum(){
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern(DATE_LONG));
    }

    public static String getDateFormatter(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_SIMPLE));
    }


    public static String getDate(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_SHORT));
    }


    public static String gerThreeRandomNum(){
        Random random = new Random();
        return random.nextInt(1000) + "";
    }

    public static String getExpireTime(Long seconds){
        return LocalDateTime.now().plusSeconds(seconds)
                .format(DateTimeFormatter.ofPattern(DATE_SIMPLE));
    }


    public static LocalDateTime parseToDateTime(String dateTime, String pattern){
        return LocalDateTime.
                parse(dateTime, DateTimeFormatter.ofPattern(pattern));
    }


}
