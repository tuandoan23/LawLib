package com.example.luat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Utils {
    public static final String HIEN_PHAP = "1";
    public static final String LUAT_SUA_DOI = "101";
    public static final String BO_LUAT_SUA_DOI = "102";
    public static final String BO_LUAT = "2";
    public static final String LUAT_HOP_NHAT = "201";
    public static final String BO_LUAT_HOP_NHAT = "202";
    public static final String LUAT = "3";
    public static final String NGHI_DINH = "4";
    public static final String NGHI_DINH_SUA_DOI = "401";
    public static final String DU_THAO_LUAT = "501";

    public static String FormatDate(String dateString){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            date = format.parse(dateString);
        } catch (ParseException e){
            e.printStackTrace();
        }
        SimpleDateFormat formatToDate = new SimpleDateFormat("dd/MM/yyyy");
        String dateTime = formatToDate.format(date);
        return dateTime;
    }

    public static String FormatDateTime(String dateString){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            date = format.parse(dateString);
        } catch (ParseException e){
            e.printStackTrace();
        }
        SimpleDateFormat formatToDate = new SimpleDateFormat("dd/MM/yyyy");
        String dateTime = formatToDate.format(date);
        return dateTime;
    }
}
