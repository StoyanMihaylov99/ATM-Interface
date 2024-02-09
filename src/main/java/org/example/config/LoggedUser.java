package org.example.config;


    //This class mimic a user session, it's singelton designed to story only one session.
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
