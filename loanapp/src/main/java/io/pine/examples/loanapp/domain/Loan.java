package io.pine.examples.loanapp.domain;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Domain object for Loan details.
 *
 * @author Srini Penchikala
 */
@Entity
@Table(name = "t_loan")
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long loanId;

	@Column(name = "product_group")
	private String productGroup;

	@Column(name = "product_id")
	private long productId;

	@Column(name = "amount")
	private BigDecimal loanAmount;

	@Column(name = "purchase_price")
	private BigDecimal purchasePrice;

	@Column(name = "status")
	private String loanStatus;

	@Column(name = "property_address")
	private String propertyAddress;

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
	 * @return the loanAmount
	 */
	public BigDecimal getLoanAmount() {
		return this.loanAmount;
	}
	/**
	 * @param loanAmount the loanAmount to set
	 */
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	/**
	 * @return the loanStatus
	 */
	public String getLoanStatus() {
		return this.loanStatus;
	}
	/**
	 * @param loanStatus the loanStatus to set
	 */
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	/**
	 * @return the productGroup
	 */
	public String getProductGroup() {
		return this.productGroup;
	}
	/**
	 * @param productGroup the productGroup to set
	 */
	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}
	/**
	 * @return the purchasePrice
	 */
	public BigDecimal getPurchasePrice() {
		return this.purchasePrice;
	}
	/**
	 * @param purchasePrice the purchasePrice to set
	 */
	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	/**
	 * @return the productId
	 */
	public long getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(long productId) {
		this.productId = productId;
	}

	/**
	 * @return the propertyAddress
	 */
	public String getPropertyAddress() {
		return propertyAddress;
	}
	/**
	 * @param propertyAddress the propertyAddress to set
	 */
	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}
	
}
