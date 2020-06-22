package com.LoadCsvIntoDb.demo.model;

import java.util.Date;

import org.jboss.logging.Logger;


public class Transaction {
private Logger logger = Logger.getLogger(Transaction.class);
    private String id;

    private String accountNumber;

    private String amount;

    private String description;

    private Date createDate;
    
    private String strCreateDate;
    
    private String strCreateTime;

    private int customerId;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Transaction [logger=");
		builder.append(logger);
		builder.append(", id=");
		builder.append(id);
		builder.append(", accountNumber=");
		builder.append(accountNumber);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", description=");
		builder.append(description);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", strCreateDate=");
		builder.append(strCreateDate);
		builder.append(", strCreateTime=");
		builder.append(strCreateTime);
		builder.append(", customerId=");
		builder.append(customerId);
		builder.append("]");
		return builder.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStrCreateDate() {
		return strCreateDate;
	}

	public void setStrCreateDate(String strCreateDate) {
		this.strCreateDate = strCreateDate;
	}

	public String getStrCreateTime() {
		return strCreateTime;
	}

	public void setStrCreateTime(String strCreateTime) {
		this.strCreateTime = strCreateTime;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

}
