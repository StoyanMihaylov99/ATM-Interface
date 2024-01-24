package org.example.services;

import org.example.Account;
import org.example.Transaction;
import org.example.config.Connector;

import java.math.BigDecimal;

public class TransactionService  {

    public static Transaction MakeATransaction (Account outAccount, Account inAccount, BigDecimal amount){
        Connector.creating();
        Transaction transaction = new Transaction(outAccount,inAccount,amount);
        Connector.getEntityManager().persist(transaction);
        Connector.commitTransaction();
        return transaction;
    }


}
