package com.kingmang.aspectra.utils;

public class StringFormator {
    public static String formatLongString(String inputString) {
        if (inputString.length() > 50) {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < inputString.length(); i += 100) {
                result.append(inputString.substring(i, Math.min(i + 100, inputString.length()))).append("\n");
            }
            return result.toString();
        } else {
            return inputString;
        }
    }
}
