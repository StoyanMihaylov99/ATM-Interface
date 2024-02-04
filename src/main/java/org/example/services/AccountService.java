package org.example.services;

import org.example.Account;
import org.example.User;
import org.example.config.Connector;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

public class AccountService {

    // Makes a new record of type "Account" to the db;
    public static Account makeNewAccount(BigDecimal startBalance, User holder) {
        Connector.transactionBegin();
        Account account;
        account = new Account(startBalance, holder);
        Connector.getEntityManager().persist(account);
        Connector.commitTransaction();
        return account;
    }

    // find a record with this iban, if noting found, return EntityNotFoundException with message;
    //this method doesn't commit any transaction, because it's used in other method for modifying records;
    public static Account findByIban(String iban) {
        Query query = Connector.getEntityManager().createQuery("FROM Account WHERE iban=: iban");
        query.setParameter("iban", iban);
        List<Account> accounts = query.getResultList();
        if (!accounts.isEmpty()) return accounts.get(0);
        return null;
    }

    // delete the record with this id;
    // The iban is for verifying;
    public static boolean deleteAccountById(String id, String iban) {
        Account account = findByIban(iban);
        if (account != null) {
            if (account.getIban().equals(iban)) {
                Connector.transactionBegin();
                Connector.getEntityManager().remove(account);
                Connector.commitTransaction();
                return true;
            }
        }
        return false;
    }

    public static boolean makeADepositByIban(BigDecimal amount, String iban) {
        Account account = findByIban(iban);
        if (account != null && !account.isBlocked()) {
            account.deposit(amount);
            Connector.transactionBegin();
            Connector.getEntityManager().merge(account);
            Connector.commitTransaction();
            return true;
        } else {
            //TODO: Display the iban is wrong;
            return false;
        }
    }

    public static boolean withdrawByIban(BigDecimal amount, String iban) {
        Account account = findByIban(iban);
        if (account != null && !account.isBlocked()) {
            if (account.withdraw(amount)) {
                Connector.transactionBegin();
                Connector.getEntityManager().merge(account);
                Connector.commitTransaction();
                return true;
            }
            return false;


        } else {
            //TODO: Display that the Iban is wrong or blocked
            return false;
        }
    }
}


