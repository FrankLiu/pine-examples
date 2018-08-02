package io.pine.examples.banking.ddd.domain.model;

import java.math.BigDecimal;

/**
 * 帐户不容许有欠款。
 *
 * @author Frank
 * @sinace 2018/7/31 0031.
 */
public class NoOverdraftPolicy implements OverdraftPolicy {

    @Override
    public boolean isAllowed(Account account, BigDecimal amount) {
        return account.getBalance().compareTo(amount) >= 0;
    }

}
