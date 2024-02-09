package org.example;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
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
    @Column(name = "iban",nullable = false)
    private String iban;
    @ManyToOne(cascade = CascadeType.ALL)
    private User holder;

    public Account(BigDecimal startBalance, User holder) {
        setBalance(startBalance);
        setIban();
        setHolder(holder);
    }

    public Account() {
    }

    public void setIban() {
        this.iban = "BG" + UUID.randomUUID().toString().toUpperCase();
    }

    public void setBalance(BigDecimal balance) {
            this.balance = balance;
    }

    public boolean withdraw(BigDecimal amount){
        if(this.balance.subtract(amount).compareTo(BigDecimal.ZERO) >= 0){
            this.balance = balance.subtract(amount);
            return true;
        }

        // Returns false if the funds are less than the amount.
        return false;
    }

    public boolean deposit(BigDecimal amount){
            this.balance = balance.add(amount);
            return true;

    }

    public void setHolder(User holder) {
        this.holder = holder;
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
