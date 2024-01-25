package org.example.services;
import org.example.User;
import org.example.config.Connector;
import org.example.config.PasswordHashing;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserService {


    // Makes a new record of type "User" to the db
    public static User makeNewUser(String firstName, String lastName, String password, String email) throws NoSuchAlgorithmException {
        Connector.transactionBegin();
        User user = new User(firstName, lastName, password, email);
        Connector.getEntityManager().persist(user);
        Connector.commitTransaction();
        return user;
    }
    // find a record with this email, if noting found, return EntityNotFoundException with message;
    //this method doesn't commit any transaction, because it's used in other method for modifying records;
    public static User findUserByEmail(String email) {
        Query query = Connector.getEntityManager().createQuery("FROM User WHERE email=: email");
        query.setParameter("email", email);
        List<User> list = query.getResultList();
        if(!list.isEmpty()) return list.get(0);
        return null;
    }
    // delete the record with this email;
    public static boolean deleteUserByEmail(String email, String pin) {
        User user = findUserByEmail(email);
        if (user != null) {
            if (PasswordHashing.verifyPassword(pin, user.getPassword())) {
                Connector.getEntityManager().remove(user);
                Connector.commitTransaction();
                return true;
            } else {
                //TODO: display that the pin is miss match;
            }
        } else {
            //TODO: display that there is no such a record with this email;
        }
        return false;
    }

    // change the email;
    public static boolean changeEmail(String email,String newEmail) {
        User user = findUserByEmail(email);
        if (user != null){
            user.setEmail(newEmail);
            Connector.getEntityManager().merge(user);
            Connector.commitTransaction();
            return true;
        } else {
            //TODO: display there is not found record with this email;
            return false;
        }
    }


}
