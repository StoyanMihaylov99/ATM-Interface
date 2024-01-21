package org.example;
import org.example.config.Connector;
import org.example.services.AccountService;
import org.example.services.UserService;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Connector.creating();
        User user = UserService.findUserByEmail("test");
        Connector.commitTransaction();
        AccountService.withdrawByIban(BigDecimal.valueOf(301),user.getBankAccounts().get(0).getIban());

    }
}