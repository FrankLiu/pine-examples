package io.pine.examples.loanapp.service;

import io.pine.examples.loanapp.LoanAppException;
import io.pine.examples.loanapp.domain.Borrower;
import io.pine.examples.loanapp.domain.FundingRequest;
import io.pine.examples.loanapp.domain.Loan;
import io.pine.examples.loanapp.domain.ProductRate;
import io.pine.examples.loanapp.dto.FundingRequestDTO;
import io.pine.examples.loanapp.repository.BorrowerRepository;
import io.pine.examples.loanapp.repository.FundingRequestRepository;
import io.pine.examples.loanapp.repository.LoanRepository;
import io.pine.examples.loanapp.repository.ProductRateRepository;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FundingServiceImpl implements FundingService {
	
	private static final Log log = LogFactory.getLog(FundingService.class);

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private BorrowerRepository borrowerRepository;

	@Autowired
	private FundingRequestRepository fundingRequestRepository;

	@Autowired
	private ProductRateRepository productRateRepository;
	
	@Autowired
	private FundingRequestDataMapper dataMapper;

	@Override
	public FundingRequestDTO getLoanFundingDetails(long loanId) throws LoanAppException {
		log.debug("getLoanFundingDetails() called.");
		
		FundingRequestDTO dto = new FundingRequestDTO();
		
		// Get funding details from the domain objects and populate the DTO.

		return dto;
	}

	@Override
	public void approveLoanFunding(FundingRequestDTO dto) throws LoanAppException {
		log.debug("approveLoanFunding() called.");
		return;
	}

	@Override
	public void processLoanFunding(FundingRequestDTO dto) throws LoanAppException {
		log.debug("dto:" + dto.toString());

		Loan newLoan = (Loan) dataMapper.getLoanFromDto(dto);
		log.debug("\nloan:" + ToStringBuilder.reflectionToString(newLoan, ToStringStyle.MULTI_LINE_STYLE));
		
		// Check loan status is valid
		String loanStatus = loan.getLoanStatus();
		
		// Check the loan amount and credit score are w/in the limits with product rate specifications
		// TODO: Get product rates.
		List<ProductRate> productRateList = productRateRepository.findAll();

		Borrower newBorrower = (Borrower) dataMapper.getBorrowerFromDto(dto);
		log.debug("\nloan:" + ToStringBuilder.reflectionToString(newBorrower, ToStringStyle.MULTI_LINE_STYLE));

		FundingRequest newFundingRequest = (FundingRequest) dataMapper.getFundingRequestFromDto(dto);
		log.debug("\nloan:" + ToStringBuilder.reflectionToString(newFundingRequest, ToStringStyle.MULTI_LINE_STYLE));
		
		// -------------------------------------------
		// Insert LOAN details
		// -------------------------------------------
		loan.add(newLoan);

		// -------------------------------------------
		// Insert BORROWER details
		// -------------------------------------------
		borrower.add(newBorrower);

		// -------------------------------------------
		// Insert FUNDING details
		// -------------------------------------------
		fundingRequest.add(newFundingRequest);

	}

}
