package io.pine.examples.banking.ddd.infrastructure;

import io.pine.examples.banking.ddd.domain.model.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Frank
 * @sinace 2018/8/28 0028.
 */
@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = { @ComponentScan.Filter(Repository.class), @ComponentScan.Filter(Service.class) })
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @Before
    public void setup() {
        accountRepository.saveAll(Arrays.asList(
                new Account("A", new BigDecimal("3250")),
                new Account("B", new BigDecimal("55.5"))
        ));
    }

    @After
    public void teardown() {

    }

    @Test
    public void testFindAll() {
        List<Account> accounts = accountRepository.findAll();

        assertNotNull(accounts);
        assertEquals(2, accounts.size());
    }

    @Test
    public void testFindById() {
        Account account = accountRepository.findByAccountId("A");

        assertNotNull(account);
        assertEquals("A", account.getAccountId());
        assertEquals(0, account.getBalance().compareTo(new BigDecimal(3250)));
    }
}
