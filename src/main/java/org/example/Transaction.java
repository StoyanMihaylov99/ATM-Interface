package org.example;
import jdk.jfr.ContentType;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string",
            strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(name = "created",nullable = false)
    private LocalDate created;
    @OneToOne
    private Account outAccount;
    @OneToOne
    private Account inAccount;
    @Column(name = "amount",nullable = false)
    private BigDecimal amount;
    @Column(name = "message",columnDefinition = "TEXT")
    private String message;

    public Transaction() {

    }

    public Transaction(Account outAccount, Account inAccount, BigDecimal amount, String  message) {
            setOutAccount(outAccount);
            setInAccount(inAccount);
            setAmount(amount);
            setTimestamp();
            setMessage(message);
    }

    public void setTimestamp() {
        this.created = LocalDate.now();
    }

    public void setOutAccount(Account outAccount) {
        this.outAccount = outAccount;
    }

    public void setInAccount(Account inAccount) {
        this.inAccount = inAccount;

    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public LocalDate getCreated() {
        return created;
    }

    public Account getOutAccount() {
        return outAccount;
    }

    public Account getInAccount() {
        return inAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
