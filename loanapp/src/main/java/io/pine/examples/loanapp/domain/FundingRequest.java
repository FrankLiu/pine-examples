package io.pine.examples.loanapp.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Domain object for Funding details.
 *
 * @author Srini Penchikala
 */

@Entity
@Table(name = "t_funding_request")
public class FundingRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long fundingTxnId;

	@Column(name = "loan_id")
	@ManyToOne(targetEntity = Loan.class, fetch = FetchType.EAGER)
	private long loanId;

	@Column(name = "type")
	private String fundType;

	@Column(name = "amount")
	private BigDecimal fundingAmount;

	@Column(name = "first_payment_date")
	@Temporal(TemporalType.DATE)
	private Date firstPaymentDate;

	@Column(name = "term_in_months")
	private int termInMonths;

	@Column(name = "monthly_payment")
	private double monthlyPayment;

	/**
	 * @return the firstPaymentDate
	 */
	public Date getFirstPaymentDate() {
		return firstPaymentDate;
	}
	/**
	 * @param firstPaymentDate the firstPaymentDate to set
	 */
	public void setFirstPaymentDate(Date firstPaymentDate) {
		this.firstPaymentDate = firstPaymentDate;
	}
	/**
	 * @return the fundingTxnId
	 */
	public long getFundingTxnId() {
		return fundingTxnId;
	}
	/**
	 * @param fundingTxnId the fundingTxnId to set
	 */
	public void setFundingTxnId(long fundingTxnId) {
		this.fundingTxnId = fundingTxnId;
	}
	/**
	 * @return the fundType
	 */
	public String getFundType() {
		return fundType;
	}
	/**
	 * @param fundType the fundType to set
	 */
	public void setFundType(String fundType) {
		this.fundType = fundType;
	}
	/**
	 * @return the fundingAmount
	 */
	public BigDecimal getLoanAmount() {
		return fundingAmount;
	}
	/**
	 * @param fundingAmount the fundingAmount to set
	 */
	public void setLoanAmount(BigDecimal fundingAmount) {
		this.fundingAmount = fundingAmount;
	}
	/**
	 * @return the loanId
	 */
	public long getLoanId() {
		return loanId;
	}
	/**
	 * @param loanId the loanId to set
	 */
	public void setLoanId(long loanId) {
		this.loanId = loanId;
	}
	/**
	 * @return the monthlyPayment
	 */
	public double getMonthlyPayment() {
		return this.monthlyPayment;
	}
	/**
	 * @param monthlyPayment the monthlyPayment to set
	 */
	public void setMonthlyPayment(double monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}
	/**
	 * @return the termInMonths
	 */
	public int getTermInMonths() {
		return termInMonths;
	}
	/**
	 * @param termInMonths the termInMonths to set
	 */
	public void setTermInMonths(int termInMonths) {
		this.termInMonths = termInMonths;
	}


}
