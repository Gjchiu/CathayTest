package com.gjchiu.currencyservice.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CommonUtil {
    public static String convertISOToTaipeiTime(String time){
        ZonedDateTime targetZonedDateTime = ZonedDateTime.parse(time).withZoneSameInstant(ZoneId.of("Asia/Taipei"));
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return targetZonedDateTime.format(outputFormatter);
    }
}
