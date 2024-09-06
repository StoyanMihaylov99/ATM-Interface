package db_object;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class User {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private  BigDecimal balance;

    public User(int id,String firstName, String lastName,String email, String password, BigDecimal balance){
        this.id =  id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }


    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal newBalance) {
        this.balance = newBalance.setScale(2, RoundingMode.CEILING);
    }
}
