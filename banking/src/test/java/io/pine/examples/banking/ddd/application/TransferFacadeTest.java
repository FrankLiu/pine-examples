package io.pine.examples.banking.ddd.application;

import io.pine.examples.banking.ddd.Application;
import io.pine.examples.banking.ddd.domain.model.Account;
import io.pine.examples.banking.ddd.domain.model.AccountUnderflowException;
import io.pine.examples.banking.ddd.domain.service.AccountNotExistedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * @author Frank
 * @sinace 2018/8/28 0028.
 */
@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TransferFacadeTest {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private TransferFacade transferFacade;

    @Test
    public void test() {
        Account fromAccount = transferFacade.createAccount("A", new BigDecimal("3250"));
        Account toAccount = transferFacade.createAccount("B", new BigDecimal("55.5"));

        try {
            transferFacade.transfer(fromAccount.getAccountId(), toAccount.getAccountId(), new BigDecimal("2000"));
        } catch (AccountNotExistedException e) {
            e.printStackTrace();
        } catch (AccountUnderflowException e) {
            e.printStackTrace();
        }

        logger.info(fromAccount.getAccountId() + " has balance: " + transferFacade.getBalance(fromAccount.getAccountId()));
        logger.info(toAccount.getAccountId() + " has balance: " + transferFacade.getBalance(toAccount.getAccountId()));
    }
}
