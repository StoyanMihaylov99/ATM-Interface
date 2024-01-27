package org.example.config;
import org.example.Account;
import org.example.User;
import java.util.List;

public class LoggedUser {
    private static String email;
    private static LoggedUser instance;

    private LoggedUser(String email) {
        LoggedUser.email = email;
    }

    public static LoggedUser getInstance(String email) {

        if (instance == null) {
            instance = new LoggedUser(email);
        }
        return instance;
    }

    public static String getEmail() {
        return email;
    }
}
