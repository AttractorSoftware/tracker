package net.itattractor.utils;

public class StringUtils {
    public static String trim(String text, String sub) {
        String result = "";
        char[] textChar = text.toCharArray();
        for (char c : textChar) {
            if (c != ' ') {
                result += c;
            }
        }

        if (result.startsWith(sub)) {
            result = result.substring(sub.length());
        }
        if (result.endsWith(sub)) {
            result = result.substring(0, result.length() - sub.length());
        }
        return result;
    }
}