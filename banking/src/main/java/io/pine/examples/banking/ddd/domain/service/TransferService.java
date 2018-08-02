package io.pine.examples.banking.ddd.domain.service;

import io.pine.examples.banking.ddd.domain.model.AccountUnderflowException;
import io.pine.examples.banking.ddd.domain.model.TransferTransaction;

import java.math.BigDecimal;

/**
 * @author Frank
 * @sinace 2018/8/1 0001.
 */
public interface TransferService {
    TransferTransaction transfer(String fromAccountId, String toAccountId, BigDecimal amount)
            throws AccountNotExistedException, AccountUnderflowException;
}
