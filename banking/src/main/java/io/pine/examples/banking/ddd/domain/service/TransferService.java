package io.pine.examples.banking.ddd.domain.service;

import io.pine.examples.banking.ddd.domain.model.Account;
import io.pine.examples.banking.ddd.domain.model.AccountUnderflowException;
import io.pine.examples.banking.ddd.domain.model.TransferTransaction;
import io.pine.examples.banking.ddd.infrastructure.AccountRepository;
import io.pine.examples.banking.ddd.infrastructure.TransferTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Frank
 * @sinace 2018/8/1 0001.
 */
@Service
public class TransferService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransferTransactionRepository transferTransactionRepository;

    @Transactional(rollbackOn = Exception.class)
    public TransferTransaction transfer(String fromAccountId, String toAccountId, BigDecimal amount) throws AccountNotExistedException, AccountUnderflowException {
        // 1. 审核转出账户和转入账户是否有效
        Account fromAccount = accountRepository.findByAccountId(fromAccountId);
        if (fromAccount == null) { throw new AccountNotExistedException(fromAccountId); }
        Account toAccount = accountRepository.findByAccountId(toAccountId);
        if (toAccount == null) { throw new AccountNotExistedException(toAccountId); }

        // 3. 转账
        // 3.1 审核转出账户金额是否足够支持转账
        fromAccount.debit(amount);
        toAccount.credit(amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        return transferTransactionRepository.save(new TransferTransaction(fromAccountId, toAccountId, amount, new Date()));
    }
}
