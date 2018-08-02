package io.pine.examples.loanapp.domain;

import javax.persistence.*;

/**
 * Domain object for Borrower.
 *
 * @author Srini Penchikala
 */
@Entity
@Table(name = "t_borrower")
public class Borrower {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long borrowerId;

	@Column(name = "borrower_type")
	private String borrowerType;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "email_address")
	private String emailAddress;

	@Column(name = "load_id")
	@ManyToOne(targetEntity = Loan.class, fetch = FetchType.EAGER)
	private long loanId;

	/**
	 * @return the borrowerId
	 */
	public long getBorrowerId() {
		return borrowerId;
	}

	/**
	 * @param borrowerId the borrowerId to set
	 */
	public void setBorrowerId(long borrowerId) {
		this.borrowerId = borrowerId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBorrowerType() {
		return this.borrowerType;
	}

	public void setBorrowerType(String borrowerType) {
		this.borrowerType = borrowerType;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return this.emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the loanId
	 */
	public long getLoanId() {
		return this.loanId;
	}

	/**
	 * @param loanId the loanId to set
	 */
	public void setLoanId(long loanId) {
		this.loanId = loanId;
	}

}
