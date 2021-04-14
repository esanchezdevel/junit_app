package esanchez.devel.junit.example.model;

/*
 * static import for be able to use all assertions methods
 * without writing the Assertions class every time
 */
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AccountTest {

	/*
	 * Simple test case with use of the 
	 * assertEquals method
	 */
	@Test
	@DisplayName("test_account_name_constructor") //the test name showed in the display
	void testAccountNameConstructor() {
		Account account = new Account("Tom", new BigDecimal("1000.12344"));
		
		String resultExpected = "Tom";
		String resultReal = account.getName();
		
		/*
		 * assertEquals for check that the expected result is the same
		 * that the result that we are getting in the code
		 */
		assertEquals(resultExpected, resultReal);
		
		/*
		 * assertTrue for check that one evaluation is true
		 */
		assertTrue(resultReal.equals("Tom"));
	}
	
	@Test
	@DisplayName("test_account_name_setter")
	void testAccountNameSetter() {
		Account account = new Account();
		account.setName("Tom");
		
		String resultExpected = "Tom";
		String resultReal = account.getName();
		
		/*
		 * assertEquals for check that the expected result is the same
		 * that the result that we are getting in the code
		 */
		assertEquals(resultExpected, resultReal);
	}
	
	@Test
	@DisplayName("test_account_balance")
	void testAccountBalance() {
		Account account = new Account("Tom", new BigDecimal("1000.12344"));
		
		assertEquals(1000.12344, account.getBalance().doubleValue());
		/*
		 * assertFalse for check that one evaluation is false
		 * compareTo returns: -1 if <, 0 if ==, 1 if >  
		 */
		assertFalse(account.getBalance().compareTo(BigDecimal.ZERO) < 0);
		assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);

	}
	
	@Test
	void testAccountReference() {
		
		Account account = new Account("John", new BigDecimal("12.0934"));
		Account account2 = new Account("Joh", new BigDecimal("12.0934"));
	
		/*
		 * assertNotEquals for check that 2 objects are not equals
		 */
		assertNotEquals(account2, account);
		//assertEquals(account2, account);
	}
}
