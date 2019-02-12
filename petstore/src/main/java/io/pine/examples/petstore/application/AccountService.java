package io.pine.examples.petstore.application;

import io.pine.examples.petstore.domain.Account;
import io.pine.examples.petstore.infrastructure.persistence.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author frank.liu
 * @since 2019/2/11 0011.
 */
@Service
public class AccountService {
    @Autowired
    private AccountMapper accountMapper;

    public Account getAccount(String username) {
        return accountMapper.getAccountByUsername(username);
    }

    public Account getAccount(Account account) {
        return accountMapper.getAccountByUsernameAndPassword(account);
    }

    public void insertAccount(Account account) {
        accountMapper.insertAccount(account);
        accountMapper.insertProfile(account);
        accountMapper.insertSignon(account);
    }

    public void updateAccount(Account account) {
        accountMapper.updateAccount(account);
        accountMapper.updateProfile(account);
        if (account.getPassword() != null && account.getPassword().length() > 0) {
            accountMapper.updateSignon(account);
        }
    }
}
