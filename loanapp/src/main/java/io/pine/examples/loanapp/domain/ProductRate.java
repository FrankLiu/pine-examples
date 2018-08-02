package io.pine.examples.loanapp.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "t_product_rate")
public class ProductRate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long productRateId;

	@Column(name = "product_group")
	private String productGroup;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "interest_rate")
	private double interestRate;

	@Column(name = "min_loan_amount")
	private BigDecimal minLoanAmount;

	@Column(name = "max_loan_amount")
	private BigDecimal maxLoanAmount;

	@Column(name = "min_credit_score")
	private int minCreditScore;

	/**
	 * @return the productRateId
	 */
	public long getProductRateId() {
		return productRateId;
	}
	/**
	 * @param productRateId the productRateId to set
	 */
	public void setProductRateId(long productRateId) {
		this.productRateId = productRateId;
	}
	/**
	 * @return the productGroup
	 */
	public String getProductGroup() {
		return productGroup;
	}
	/**
	 * @param productGroup the productGroup to set
	 */
	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the interestRate
	 */
	public double getInterestRate() {
		return interestRate;
	}
	/**
	 * @param interestRate the interestRate to set
	 */
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	/**
	 * @return the minLoanAmount
	 */
	public BigDecimal getMinLoanAmount() {
		return minLoanAmount;
	}
	/**
	 * @param minLoanAmount the minLoanAmount to set
	 */
	public void setMinLoanAmount(BigDecimal minLoanAmount) {
		this.minLoanAmount = minLoanAmount;
	}
	/**
	 * @return the maxLoanAmount
	 */
	public BigDecimal getMaxLoanAmount() {
		return maxLoanAmount;
	}
	/**
	 * @param maxLoanAmount the maxLoanAmount to set
	 */
	public void setMaxLoanAmount(BigDecimal maxLoanAmount) {
		this.maxLoanAmount = maxLoanAmount;
	}
	/**
	 * @return the minCreditScore
	 */
	public int getMinCreditScore() {
		return minCreditScore;
	}
	/**
	 * @param minCreditScore the minCreditScore to set
	 */
	public void setMinCreditScore(int minCreditScore) {
		this.minCreditScore = minCreditScore;
	}


}
