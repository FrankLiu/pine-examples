package io.pine.examples.loanapp.repository;

import io.pine.examples.loanapp.domain.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {

}
