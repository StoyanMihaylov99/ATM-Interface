package org.example.services;

import jakarta.persistence.Query;
import org.example.Account;
import org.example.Transaction;
import org.example.User;
import org.example.config.Connector;
import org.example.config.SelectedAccount;
import java.math.BigDecimal;
import java.util.List;

public class TransactionService  {

    // Makes and persist a new record of type "Transaction" to the db
    public static Transaction persistATransaction (Account outAccount, Account inAccount, BigDecimal amount, String message){
        Connector.transactionBegin();
        Transaction transaction = new Transaction(outAccount,inAccount,amount, message);
        Connector.getEntityManager().persist(transaction);
        Connector.commitTransaction();
        return transaction;
    }

    //Verify the amount, it should be a positive amount and be less than the account balance.
    public static boolean verifyTransaction(Account outAccount, Account inAccount, BigDecimal amount){
        return outAccount.getBalance().compareTo(amount) > 0;

    }

    public static List<Transaction> findTransactionsIn(){
        Account account = AccountService.findByIban(SelectedAccount.getIban());
        Query query = Connector.getEntityManager().createQuery("FROM Transaction WHERE inAccount = :inAccountId");
        query.setParameter("inAccountId",account);
        List<Transaction> list = query.getResultList();
        return list;
    }

    public static List<Transaction> findTransactionsOut(){
        Account account = AccountService.findByIban(SelectedAccount.getIban());
        Query query = Connector.getEntityManager().createQuery("FROM Transaction WHERE outAccount = :account");
        query.setParameter("account", account);
        List<Transaction> list = query.getResultList();
        return list;
    }


}
