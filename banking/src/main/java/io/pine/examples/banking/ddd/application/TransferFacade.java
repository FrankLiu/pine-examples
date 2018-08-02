package io.pine.examples.banking.ddd.application;

import io.pine.examples.banking.ddd.domain.model.Account;
import io.pine.examples.banking.ddd.domain.model.AccountUnderflowException;
import io.pine.examples.banking.ddd.domain.model.TransferTransaction;
import io.pine.examples.banking.ddd.domain.service.AccountNotExistedException;

import java.math.BigDecimal;

/**
 * @author Frank
 * @sinace 2018/8/1 0001.
 */
public interface TransferFacade {
    TransferTransaction transfer(String fromAccountId, String toAccountId, BigDecimal amount)
            throws AccountNotExistedException, AccountUnderflowException;

    Account createAccount(String accountId, BigDecimal balance);

    BigDecimal getBalance(String accountId);
}
