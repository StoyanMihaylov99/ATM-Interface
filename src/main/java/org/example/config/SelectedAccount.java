package org.example.config;
import org.example.services.AccountService;
import java.math.BigDecimal;

    //This is a class to mimic the account session, and pass the information through controllers and views.
public class SelectedAccount {
    private static String id;
    private static String iban;
    private static BigDecimal balance;

    public SelectedAccount(String iban) {
        setIban(iban);
        setBalance(AccountService.findByIban(iban).getBalance());
        setId(AccountService.findByIban(iban).getId());
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        SelectedAccount.id = id;
    }

    public static String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        SelectedAccount.iban = iban;
    }

    public static BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        SelectedAccount.balance = balance;
    }
}
