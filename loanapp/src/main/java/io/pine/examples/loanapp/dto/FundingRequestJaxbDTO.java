package io.pine.examples.loanapp.dto;


import io.pine.examples.loanapp.domain.Borrower;
import io.pine.examples.loanapp.domain.FundingRequest;
import io.pine.examples.loanapp.domain.Loan;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="funding-request")
@XmlAccessorType(XmlAccessType.FIELD)
public class FundingRequestJaxbDTO {
	
	@XmlElements({
		@XmlElement(name="loan-info", type=Loan.class),
		@XmlElement(name="borrower", type=Borrower.class),
		@XmlElement(name="funding-info", type=FundingRequest.class)
	})

	@Override
    public String toString()
    {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
