package org.example;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string",
            strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(name = "balance",nullable = false)
    private BigDecimal balance;
    @Column(name = "is_blocked",nullable = false)
    private boolean isBlocked;
    @Column(name = "iban",nullable = false)
    private String iban;
    @ManyToOne
    private User holder;

    public Account(BigDecimal startBalance, User holder) {
        setBalance(startBalance);
        setBlocked(false);
        setIban(this.iban);
        setHolder(holder);
    }

    public Account() {
    }

    public void setIban(String iban) {
        this.iban = "BG" + UUID.randomUUID().toString().toUpperCase();
    }

    public void setBalance(BigDecimal balance) {
        if (balance.doubleValue() < 0) {
            //TODO: error display
        } else {
            this.balance = balance;
        }
    }

    public boolean withdraw(BigDecimal amount){
        if(this.balance.subtract(amount).compareTo(BigDecimal.ZERO) > 0 && !isBlocked){
            this.balance = balance.subtract(amount);
            return true;
        } else {
            System.out.println("not enough finds");
            return false;
            //TODO: Display that there is not enough funds for this withdraw;
        }
    }

    public boolean deposit(BigDecimal amount){
        if(!isBlocked){
            this.balance = balance.add(amount);
            return true;
        } else {
            return false;
            //TODO: Display that the account is blocked.
        }
    }

    public void setHolder(User holder) {
        this.holder = holder;
    }

    public void setBlocked(boolean blocked) {
        this.isBlocked = blocked;
    }


    public boolean isBlocked() {
        return isBlocked;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public User getHolder() {
        return holder;
    }

    @Override
    public String toString() {
        return this.iban;
    }
}
