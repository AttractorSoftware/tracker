package net.itattractor.utils;

public class StringUtils {
    public static String trim(String str, String subStr) {
        String destination = str;
        if (str.endsWith(subStr)) {
            destination = str.substring(0, str.length() - 1);
        }
        return destination;
    }
}