package io.pine.examples.loanapp.repository;


import io.pine.examples.loanapp.domain.ProductRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRateRepository extends JpaRepository<ProductRate, Long> {

}
