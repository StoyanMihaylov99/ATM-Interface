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
@Table(name = "transactons")
public class Transaction {
    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string",
            strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(name = "created",nullable = false)
    private LocalDate created;
    @Column(name = "out_account",nullable = false)
    @OneToMany
    private List<Account> outAccount;
    @OneToMany
    @Column(name = "in_account",nullable = false)
    private List<Account> inAccount;
    @Column(name = "amount",nullable = false)
    private BigDecimal amount;

    public Transaction() {

    }

    public void setTimestamp() {
        this.created = LocalDate.now();
    }

    public void setOutAccount(ArrayList<Account> outAccount) {
        this.outAccount = outAccount;
    }

    public void setInAccount(ArrayList<Account> inAccount) {
        this.inAccount = inAccount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isApproved(Account inAccount, Account outAccount, BigDecimal amount){
        if(!inAccount.isBlocked() && !outAccount.isBlocked()){
            if(outAccount.getBalance().doubleValue() < amount.doubleValue()){
                //TODO: Display there is not enough money in outAccount;
                return false;
            } else {
                return true;
            }
        } else {
            //TODO: Display there is a blocked account;
            return false;
        }
    }
}
