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
    @Column(name = "out_account",nullable = false)
    @OneToOne
    private Account outAccount;
    @OneToOne
    @Column(name = "in_account",nullable = false)
    private Account inAccount;
    @Column(name = "amount",nullable = false)
    private BigDecimal amount;

    public Transaction() {

    }

    public Transaction(Account outAccount, Account inAccount, BigDecimal amount) {
            setOutAccount(outAccount);
            setInAccount(inAccount);
            setAmount(amount);
            setTimestamp();
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

    public boolean isApproved(){
            if(this.outAccount.getBalance().doubleValue() < amount.doubleValue()){
                return false;
            }
                return true;
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
}
