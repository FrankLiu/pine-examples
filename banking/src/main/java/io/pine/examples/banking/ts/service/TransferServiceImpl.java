package io.pine.examples.banking.ts.service;

import io.pine.examples.banking.ts.model.Account;
import io.pine.examples.banking.ts.model.TransferTransaction;
import io.pine.examples.banking.ts.repository.AccountRepository;
import io.pine.examples.banking.ts.repository.TransferTransactionRepository;
import org.apache.commons.lang3.Validate;
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
public class TransferServiceImpl implements TransferService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransferTransactionRepository transferTransactionRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TransferTransaction transfer(String fromAccountId, String toAccountId, BigDecimal amount) throws AccountNotExistedException, AccountUnderflowException {
        // 1. 判断金额 > 0
        Validate.isTrue(amount.compareTo(BigDecimal.ZERO) > 0);

        // 2. 审核转出账户和转入账户是否有效
        Account fromAccount = accountRepository.findByAccountId(fromAccountId);
        if (fromAccount == null) { throw new AccountNotExistedException(fromAccountId); }
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new AccountUnderflowException(fromAccount, amount);
        }

        Account toAccount = accountRepository.findByAccountId(toAccountId);
        if (toAccount == null) { throw new AccountNotExistedException(toAccountId); }

        // 3. 转账
        // 3.1 审核转出账户金额是否足够支持转账
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        // 4. 保存数据
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        return transferTransactionRepository.save(new TransferTransaction(fromAccountId, toAccountId, amount, new Date()));
    }
}
