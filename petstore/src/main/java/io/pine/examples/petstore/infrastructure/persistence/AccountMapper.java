package io.pine.examples.petstore.infrastructure.persistence;

import io.pine.examples.petstore.domain.Account;
import org.springframework.stereotype.Repository;

/**
 * @author frank.liu
 * @since 2019/2/11 0011.
 */
@Repository
public interface AccountMapper {

    /**
     * 通过用户名得到用户
     */
    Account getAccountByUsername(String username);

    /**
     * 通过用户名和密码得到用户
     */
    Account getAccountByUsernameAndPassword(Account account);

    /**
     * 插入新用户
     */
    void insertAccount(Account account);

    /**
     * 插入新用户的部分信息
     */
    void insertProfile(Account account);

    /**
     * 插入新用户的登录信息
     */
    void insertSignon(Account account);

    /**
     * 更新用户
     */
    void updateAccount(Account account);

    /**
     * 更新用户的部分信息
     */
    void updateProfile(Account account);

    /**
     * 更新用户的登录信息
     */
    void updateSignon(Account account);
}
