package io.pine.examples.banking.ddd.domain.model;

import org.apache.commons.lang3.Validate;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Frank
 * @sinace 2018/7/31 0031.
 */

@Entity
@Table(name = "fin_account")
public class Account {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id")
    private String accountId;

    @Column
    private BigDecimal balance;

    @Transient
    private OverdraftPolicy overdraftPolicy = new NoOverdraftPolicy();

    public Account() {}

    public Account(String accountId, BigDecimal balance) {
        Validate.notEmpty(accountId);
        Validate.isTrue(balance == null || balance.compareTo(BigDecimal.ZERO) >= 0);

        this.accountId = accountId;
        this.balance = (balance == null ? BigDecimal.ZERO : balance);
    }

    public String getAccountId() {
        return accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void credit(BigDecimal amount) {
        Validate.isTrue(amount.compareTo(BigDecimal.ZERO) > 0);

        balance = balance.add(amount);
    }

    public void debit(BigDecimal amount) throws AccountUnderflowException {
        Validate.isTrue(amount.compareTo(BigDecimal.ZERO) > 0);

        if(!overdraftPolicy.isAllowed(this, amount)) {
            throw new AccountUnderflowException(this, amount);
        }
        balance = balance.subtract(amount);
    }

    @Override
    public String toString() {
        return this.accountId;
    }
}
