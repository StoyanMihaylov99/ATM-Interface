package org.example.config;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AmountValidator {

    private static final String DECIMAL_PATTERN =
            "^\\d+(\\.\\d+)?$";

    private static final Pattern pattern = Pattern.compile(DECIMAL_PATTERN);

    public static boolean validateAmount(String amount) {
        Matcher matcher = pattern.matcher(amount);
        return matcher.matches();
    }

}
