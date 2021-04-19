package esanchez.devel.junit.example.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BankTest {

	@Test
	@DisplayName("test_transfer_money_accounts")
	void testTransferMoneyAccounts() {
		
		Account account1 = new Account("John", new BigDecimal("1000"));
		Account account2 = new Account("Tom", new BigDecimal("1500"));
		
		Bank bank = new Bank();
		bank.setName("BBVA");
		bank.transfer(account2, account1, new BigDecimal("400"));
		assertEquals(new BigDecimal("1100"), account2.getBalance());
		assertEquals(new BigDecimal("1400"), account1.getBalance());
	}
	
	@Test
	@DisplayName("test_relation_bank_account")
	void testRelationBankAccount() {
		
		Account account1 = new Account("John", new BigDecimal("1000"));
		Account account2 = new Account("Tom", new BigDecimal("1500"));
		
		Bank bank = new Bank();
		bank.addAccount(account1);
		bank.addAccount(account2);
		
		bank.setName("BBVA");
		bank.transfer(account2, account1, new BigDecimal("400"));
		
		/*
		 * assertAll for make multiple asserts, and receive the report of 
		 * all asserts that fails without stop the execution.
		 * The assertAll include a list of lambda expressions, and in each 
		 * lambda expression we must include one assert
		 */
		assertAll(
				() -> {
					/*
					 * We can add an extra parameter with a lambda expression and a string that will be a custom
					 * error message to show when the assert fails.
					 */
					assertEquals(new BigDecimal("1100"), account2.getBalance(), () -> "account2 wrong balance");
					}, 
				() -> {assertEquals(new BigDecimal("1400"), account1.getBalance(), () -> "account1 wrong balance");}, 
				() -> {assertEquals(2, bank.getAccounts().size(), () -> "the size of the list is wrong");}, 
				() -> {assertEquals("BBVA", account1.getBank().getName(), () -> "the account1 name is not the expected");}, 
				() -> {assertEquals("BBVA", account2.getBank().getName(), () -> "the account2 name is not the expected");}, 
				() -> {
					/*
					 * test that bank have one account with name=Tom.
					 * 1-convert the list in a stream
					 * 2-filter using a lambda expression
					 * 3-findFirst for get the first result.
					 * 4-findFirst returns an Optional so we need to call to .get()
					 */
					assertEquals("Tom", bank.getAccounts().stream()
							.filter(a -> a.getName().equals("Tom"))
							.findFirst()
							.get().getName());
				}, 
				() -> {
					/*
					 * same test but using assertTrue with the isPresent of the Optional
					 */
					assertTrue(bank.getAccounts().stream()
							.filter(a -> a.getName().equals("Tom"))
							.findFirst()
							.isPresent());
				},
				() -> {
					/*
					 * another alternative way to check that there is an account
					 * with name=Tom
					 */
					assertTrue(bank.getAccounts().stream()
							.anyMatch(c -> c.getName().equals("Tom")));
				});
	}
}
