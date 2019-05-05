package com.ebiz.common;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class GeneralMethod {
    public static String getTimeStringForSeconds(long time) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        gc.setTimeInMillis(time*1000);
        int year=gc.get(Calendar.YEAR);
        int month=gc.get(Calendar.MONTH) + 1;
        int day=gc.get(Calendar.DAY_OF_MONTH);
        int hour=gc.get(Calendar.HOUR_OF_DAY);
        int minute=gc.get(Calendar.MINUTE);
        int second=gc.get(Calendar.SECOND);
        return  pad(year)+"-"+pad(month)+"-"+pad(day)+" "+pad(hour)+":"+pad(minute)+":"+pad(second);//+" "+ gc.getTimeZone().getDisplayName(false, TimeZone.SHORT);
    }
    public static String pad(int val) {
        return val < 10 ? "0" + val : "" + val;
    }
}
