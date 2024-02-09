package org.example.config;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


    //This class is used to hashing the user password. The application stores only the generated hashcode.
public class PasswordHashing {
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(
                    password.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

     // This method verify the entered password, and the hashcode of the existing one.

    public static boolean verifyPassword(String enteredPassword, String storedHash) {
        String enteredPasswordHash = hashPassword(enteredPassword);
        return enteredPasswordHash.equals(storedHash);
    }
}