package io.pine.examples.banking.ts;

import io.pine.examples.banking.ts.facade.TransferFacade;
import io.pine.examples.banking.ts.model.Account;
import io.pine.examples.banking.ts.service.AccountNotExistedException;
import io.pine.examples.banking.ts.service.AccountUnderflowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

/**
 * @author Frank
 * @sinace 2018/8/1 0001.
 */
@SpringBootApplication
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(TransferFacade transferFacade) {
        return (args) -> {
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
        };
    }
}
