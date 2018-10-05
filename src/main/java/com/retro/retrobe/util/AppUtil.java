package com.retro.retrobe.util;

public class AppUtil {
    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static String getUserFullName(String firstName, String lastName) {
        if (lastName != null) {
            return firstName + ' ' + lastName;
        }
        return firstName;
    }

    public static String abbreviation(String fullName) {
        String[] arrStr = fullName.split(" ");
        StringBuilder abbr = new StringBuilder();
        for (String abc : arrStr) {
            abbr.append(abc.substring(0, 1));
        }
        return abbr.toString();
    }
}
