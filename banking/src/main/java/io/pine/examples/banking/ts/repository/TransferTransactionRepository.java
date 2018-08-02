package io.pine.examples.banking.ts.repository;

import io.pine.examples.banking.ts.model.TransferTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Frank
 * @sinace 2018/8/1 0001.
 */
@Repository
public interface TransferTransactionRepository extends JpaRepository<TransferTransaction, Long> {

}
