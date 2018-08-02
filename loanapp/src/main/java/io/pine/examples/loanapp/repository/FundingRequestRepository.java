package io.pine.examples.loanapp.repository;

import io.pine.examples.loanapp.domain.FundingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundingRequestRepository extends JpaRepository<FundingRequest, Long> {

}
