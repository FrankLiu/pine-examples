package io.pine.examples.banking.ddd.domain.model;

import java.math.BigDecimal;

/**
 * @author Frank
 * @sinace 2018/7/31 0031.
 */
public interface OverdraftPolicy {
    boolean isAllowed(Account account, BigDecimal amount);
}
