package com.ebiz.utils;

public class StringUtils {

    public static boolean isEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    public static boolean contains(String userAgent, String msie) {
        return userAgent.contains(msie.trim());
    }
}
