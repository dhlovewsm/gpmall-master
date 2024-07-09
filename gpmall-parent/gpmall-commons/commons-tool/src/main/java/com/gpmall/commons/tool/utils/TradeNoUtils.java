package com.gpmall.commons.tool.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class TradeNoUtils {




    public static String generateTradeNo() {

        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String seconds = now.format(DateTimeFormatter.ofPattern("HHmmss"));
        String millSeconds = now.format(DateTimeFormatter.ofPattern("SSS"));

        return date + "00001000" + millSeconds + getTwoRandomNum() +"00" + seconds + getTwoRandomNum() + millSeconds;

    }

    private static String getTwoRandomNum() {

        Random random = new Random();

        String result = random.nextInt(100) + "";
        if (result.length() == 1){
            result = "0" + result;
        }
        return result;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(generateTradeNo());
        }

    }


}
