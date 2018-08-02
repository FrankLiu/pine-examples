package io.pine.examples.banking.ts.service;


import io.pine.examples.banking.ts.model.TransferTransaction;

import java.math.BigDecimal;

/**
 * @author Frank
 * @sinace 2018/8/1 0001.
 */
public interface TransferService {
    TransferTransaction transfer(String fromAccountId, String toAccountId, BigDecimal amount)
            throws AccountNotExistedException, AccountUnderflowException;
}
