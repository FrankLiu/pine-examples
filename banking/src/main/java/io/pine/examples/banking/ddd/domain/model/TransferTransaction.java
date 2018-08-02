package io.pine.examples.banking.ddd.domain.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Frank
 * @sinace 2018/7/31 0031.
 */
@Entity
@Table(name = "transfer_transaction")
public class TransferTransaction {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trans_time")
    private Date timestamp;

    @Column(name = "from_account_id")
    private String fromAccountId;

    @Column(name = "to_account_id")
    private String toAccountId;

    private BigDecimal amount;

    public TransferTransaction() {}

    public TransferTransaction(String fromAccountId, String toAccountId, BigDecimal amount, Date timestamp) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("transfer '%s' from account '%s' to account '%s' at '%s'", amount, fromAccountId, toAccountId, timestamp);
    }
}
