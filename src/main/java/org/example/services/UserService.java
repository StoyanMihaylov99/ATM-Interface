package org.example.services;
import jakarta.persistence.Query;
import org.example.Account;
import org.example.User;
import org.example.config.Connector;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserService {


    // Makes and persist a new record of type "User" to the db
    public static User makeNewUser(String firstName, String lastName, String password, String email) throws NoSuchAlgorithmException {
        Connector.transactionBegin();
        User user = new User(firstName, lastName, password, email);
        Connector.getEntityManager().persist(user);
        Connector.commitTransaction();
        Account firstAccount = AccountService.makeNewAccount(BigDecimal.valueOf(0),user);
        return user;
    }
    // find a record with this email, if noting found, null;
    //this method doesn't commit any transaction, because it's used in other method for modifying records;
    public static User findUserByEmail(String email) {
        Query query = Connector.getEntityManager().createQuery("FROM User WHERE email=: email");
        query.setParameter("email", email);
        List<User> list = query.getResultList();
        if(!list.isEmpty()) return list.get(0);
        return null;
    }

    // delete the record with this email;
    public static boolean deleteUserByEmail(String email) {
        User user = findUserByEmail(email);
        if (user != null) {

            Connector.transactionBegin();
            Query q = Connector.getEntityManager().createQuery("DELETE FROM User WHERE email =: email");
            q.setParameter("email",email);
            q.executeUpdate();
            Connector.commitTransaction();
                return true;
            }
        return false;
    }
}
