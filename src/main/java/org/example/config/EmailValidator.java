package org.example.config;
import org.example.services.UserService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


    //This class is for registration method(email field), it's validate the input data.
public class EmailValidator {

    private static final String EMAIL_PATTERN =
            "^^[\\w\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean verifyDuplicateEmail(String email) {
        Connector.creating();
        return UserService.findUserByEmail(email) != null;


    }
}
