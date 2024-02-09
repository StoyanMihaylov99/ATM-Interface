package org.example.config;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

    // This is a decimal number validator (regular expression), it's used on input fields to validate the decimal amount;
public class AmountValidator {

    private static final String DECIMAL_PATTERN =
            "^\\d+(\\.\\d+)?$";

    private static final Pattern pattern = Pattern.compile(DECIMAL_PATTERN);

    public static boolean validateAmount(String amount) {
        Matcher matcher = pattern.matcher(amount);
        return matcher.matches();
    }

}
