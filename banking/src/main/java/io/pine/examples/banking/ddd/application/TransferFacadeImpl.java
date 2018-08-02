package io.pine.examples.banking.ddd.application;

import io.pine.examples.banking.ddd.domain.model.Account;
import io.pine.examples.banking.ddd.domain.model.AccountUnderflowException;
import io.pine.examples.banking.ddd.domain.model.TransferTransaction;
import io.pine.examples.banking.ddd.domain.service.AccountNotExistedException;
import io.pine.examples.banking.ddd.domain.service.TransferService;
import io.pine.examples.banking.ddd.infrastructure.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Frank
 * @sinace 2018/8/1 0001.
 */
@Service
public class TransferFacadeImpl implements TransferFacade {
    @Autowired
    private TransferService transferService;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public TransferTransaction transfer(String fromAccountId, String toAccountId, BigDecimal amount) throws AccountNotExistedException, AccountUnderflowException {
        return transferService.transfer(fromAccountId, toAccountId, amount);
    }

    @Override
    public Account createAccount(String accountId, BigDecimal balance) {
        return accountRepository.save(new Account(accountId, balance));
    }

    @Override
    public BigDecimal getBalance(String accountId) {
        return accountRepository.findByAccountId(accountId).getBalance();
    }
}
