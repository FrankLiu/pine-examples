package io.pine.examples.banking.ts.facade;

import io.pine.examples.banking.ts.model.Account;
import io.pine.examples.banking.ts.model.TransferTransaction;
import io.pine.examples.banking.ts.service.AccountNotExistedException;
import io.pine.examples.banking.ts.service.AccountUnderflowException;

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
