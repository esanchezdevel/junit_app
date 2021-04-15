package esanchez.devel.junit.example.model;

import java.math.BigDecimal;

import esanchez.devel.junit.example.exception.InsufficientBalanceException;

public class Account {

	private String name;
	private BigDecimal balance;

	public Account() {}
	
	public Account(String name, BigDecimal balance) {
		this.balance = balance;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public void debit(BigDecimal amount) throws InsufficientBalanceException {
		
		if (amount.compareTo(this.balance) == -1) {
			this.balance = this.balance.subtract(amount);
		} else {
			throw new InsufficientBalanceException("insufficient balance");
		}
	}
	
	public void credit(BigDecimal amount) {
		this.balance = this.balance.add(amount);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null || !(obj instanceof Account))
			return false;
		
		Account account = (Account) obj;
		
		if (this.name == null || this.balance == null)
			return false;
		
		return this.name.equals(account.getName()) && this.balance.equals(account.getBalance());
	}
	
	
}
