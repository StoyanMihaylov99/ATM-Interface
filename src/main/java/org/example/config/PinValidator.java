package org.example.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PinValidator {
    private static final String PATTERN = "^\\d{4}$";
    private static final Pattern pattern = Pattern.compile(PATTERN);

    public static boolean validatePin(String pin) {
        Matcher matcher = pattern.matcher(pin);
        return matcher.matches();
    }
}
