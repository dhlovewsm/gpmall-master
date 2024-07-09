package com.gpmall.commons.tool.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {


    public static double toDouble(BigDecimal data){
        return data.setScale(2, RoundingMode.DOWN).doubleValue();
    }

}
