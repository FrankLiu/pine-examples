package io.pine.examples.banking.ddd.domain.model;

import java.math.BigDecimal;

/**
 * 当取的金额超过帐户容许的最大金额时，会抛出此异常。
 *
 * @author Frank
 * @sinace 2018/7/31 0031.
 */
public class AccountUnderflowException extends Exception {
    private static final long serialVersionUID = -6299588017190080876L;

    private Account account;
    private BigDecimal amount;

    public AccountUnderflowException(Account account, BigDecimal amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public String getMessage() {
        return String.format("Not allowed to debit '%s' from account '%s'", amount, account);
    }
}
