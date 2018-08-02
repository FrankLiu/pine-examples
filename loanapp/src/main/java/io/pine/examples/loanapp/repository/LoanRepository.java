package io.pine.examples.loanapp.repository;

import io.pine.examples.loanapp.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {

}
