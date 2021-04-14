package esanchez.devel.junit.example.model;

import java.math.BigDecimal;

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
}
