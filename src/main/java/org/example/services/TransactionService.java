package org.example.services;

import org.example.Account;
import org.example.Transaction;
import org.example.config.Connector;

import java.math.BigDecimal;

public class TransactionService  {

    public static Transaction persistATransaction (Account outAccount, Account inAccount, BigDecimal amount, String message){
        Connector.transactionBegin();
        Transaction transaction = new Transaction(outAccount,inAccount,amount, message);
        Connector.getEntityManager().persist(transaction);
        Connector.commitTransaction();
        return transaction;
    }

    public static boolean verifyTransaction(Account outAccount, Account inAccount, BigDecimal amount){
        return outAccount.getBalance().compareTo(amount) > 0;

    }


}
