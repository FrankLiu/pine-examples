package io.pine.examples.banking.ddd.domain.model;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;

/**
 * @author Frank
 * @sinace 2018/7/31 0031.
 */
public class LimitedOverdraftPolicy implements OverdraftPolicy {

    private BigDecimal limit;

    public LimitedOverdraftPolicy(BigDecimal limit) {
        Validate.isTrue(limit.compareTo(BigDecimal.ZERO) > 0, "limit must be greater than zero");
        this.limit = limit;
    }

    @Override
    public boolean isAllowed(Account account, BigDecimal amount) {
        return account.getBalance().add(this.limit).compareTo(amount) > 0;
    }
}
