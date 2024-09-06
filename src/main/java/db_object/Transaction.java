package db_object;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private final int userId;
    private final BigDecimal amount;
    private final String type;
    private final Date date;

    public Transaction(int id, String type, BigDecimal amount, Date date) {
        this.userId = id;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }
}
