package io.pine.examples.loanapp.service;

import io.pine.examples.loanapp.LoanAppException;
import io.pine.examples.loanapp.dto.FundingRequestDTO;

public interface FundingService {

	public FundingRequestDTO getLoanFundingDetails(long loanId) throws LoanAppException;

	public void approveLoanFunding(FundingRequestDTO dto) throws LoanAppException;

	public void processLoanFunding(FundingRequestDTO fundingRequestDto) throws LoanAppException;

}
