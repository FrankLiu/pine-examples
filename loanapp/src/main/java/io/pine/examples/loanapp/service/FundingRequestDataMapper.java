package io.pine.examples.loanapp.service;


import io.pine.examples.loanapp.domain.Borrower;
import io.pine.examples.loanapp.domain.FundingRequest;
import io.pine.examples.loanapp.domain.Loan;
import io.pine.examples.loanapp.dto.FundingRequestDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

public class FundingRequestDataMapper {
	
	private static final Log log = LogFactory.getLog(FundingRequestDataMapper.class);
	
	@Autowired
	private Mapper mapper;

	public Loan getLoanFromDto(FundingRequestDTO dto) {
		log.debug("dto:" + dto.toString());

		Loan loan = (Loan) mapper.map(dto, Loan.class);
		log.debug("\nloan:" + ToStringBuilder.reflectionToString(loan, ToStringStyle.MULTI_LINE_STYLE));
		
		return loan;
	}

	public Borrower getBorrowerFromDto(FundingRequestDTO dto) {
		log.debug("dto:" + dto.toString());

		Borrower borrower = (Borrower) mapper.map(dto, Borrower.class);
		log.debug("\borrower:" + ToStringBuilder.reflectionToString(borrower, ToStringStyle.MULTI_LINE_STYLE));
		
		return borrower;
	}

	public FundingRequest getFundingRequestFromDto(FundingRequestDTO dto) {
		log.debug("dto:" + dto.toString());

		FundingRequest fundingRequest = (FundingRequest) mapper.map(dto, FundingRequest.class);
		log.debug("\fundingRequest:" + ToStringBuilder.reflectionToString(fundingRequest, ToStringStyle.MULTI_LINE_STYLE));
		
		return fundingRequest;
	}

}
