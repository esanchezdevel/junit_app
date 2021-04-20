package esanchez.devel.junit.example.model;

/*
 * static import for be able to use all assertions methods
 * without writing the Assertions class every time
 */
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Properties;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfSystemProperties;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

import esanchez.devel.junit.example.exception.InsufficientBalanceException;

class AccountTest {

	Account account;
	
	@BeforeAll
	static void setUp() {
		System.out.println("Initializing class");
	}
	
	@AfterAll
	static void afterAll() {
		System.out.println("Finalizing class");
	}
	
	@BeforeEach
	void initMethodTest() {
		System.out.println("Initializing method");
		
		this.account = new Account("Tom", new BigDecimal("1000.12344"));
	}
	
	@AfterEach
	void finishMethodTest() {
		System.out.println("finalizing method");
	}
	
	/*
	 * Simple test case with use of the 
	 * assertEquals method
	 */
	@Test
	@DisplayName("test_account_name_constructor") //the test name showed in the display
	void testAccountNameConstructor() {		
		String resultExpected = "Tom";
		String resultReal = this.account.getName();
		
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
		assertEquals(1000.12344, this.account.getBalance().doubleValue());
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
	
	/*
	 * Tests done with TDD
	 * 1- create the empty methods "debit()" and "credit()" in Account.java without any funcionality
	 * 2- create the tests "test_debit_account" and "test_credit_account"
	 * 3- execute the tests, and fails because the methods was not implemented yet
	 * 4- implement the methods debit and credit in Account.java for make the tests works
	 * 5- execute the tests, and now are working :)
	 */
	@Test
	@DisplayName("test_debit_account")
	void testDebitAccount() {
		Account account = new Account("Tom", new BigDecimal("12.23456"));
		account.debit(new BigDecimal("2"));
		/*
		 * assertNotNull for check that an object is not null
		 */
		assertNotNull(account.getBalance());
		assertEquals(new BigDecimal("10.23456"), account.getBalance());
	}
	
	@Test
	@DisplayName("test_credit_account")
	void testCreditAccount() {
		Account account = new Account("Tom", new BigDecimal("12.23456"));
		account.credit(new BigDecimal("2"));
		assertNotNull(account.getBalance());
		assertEquals(new BigDecimal("14.23456"), account.getBalance());
	}
	
	/*
	 * Test an exception with assertThrows
	 */
	@Test
	@DisplayName("test_insufficient_balance_exception")
	void testInsufficientBalanceException() {
		Account account = new Account("Tom", new BigDecimal("12.23456"));
		
		/*
		 * first test that the exception is thrower.
		 * the code that should throw the exception must be in a lambda expression
		 */
		Exception exception = assertThrows(InsufficientBalanceException.class, ()->{
			account.debit(new BigDecimal("14"));
		});
		
		/*
		 * now we can test that the message printed by the exception is
		 * the message that we expected
		 */
		String message = exception.getMessage();
		String expected = "insufficient balance";
		
		assertEquals(expected, message);
	}
	
	/*
	 * The @EnabledOn... tags help us to execute tests only in some
	 * scenarios. For example if we only want to execute one test
	 * when we are in a windows pc. 
	 */
	@Test
	@DisplayName("test_only_windows")
	@EnabledOnOs(OS.WINDOWS)
	void testOnlyWindow() {
		
	}
	
	@Test
	@DisplayName("test_only_linux_and_mac")
	@EnabledOnOs({OS.LINUX, OS.MAC})
	void testOnlyLinuxMac() {
		
	}
	
	/*
	 * with @DisabledOn... tag we have the same functionality of
	 * @EnabledOn but in the oposite. 
	 */
	@Test
	@DisplayName("test_no_windows")
	@DisabledOnOs(OS.WINDOWS)
	void testNoWindow() {
		
	}
	
	@Test
	@DisplayName("test_no_linux_and_mac")
	@DisabledOnOs({OS.LINUX, OS.MAC})
	void testNoLinuxMax() {
		
	}
	
	@Test
	@DisplayName("test_on_java8")
	@EnabledOnJre(JRE.JAVA_8)
	void testOnJava8() {
		
	}
	
	@Test
	@DisplayName("test_on_java11")
	@EnabledOnJre(JRE.JAVA_11)
	void testOnJava11() {
		
	}
	
	/*
	 * method for print the available system properties for use in
	 * the next test
	 */
	
	@Test
	@DisplayName("test_print_properties")
	@Disabled
	void testPrintProperties() {
		
		Properties properties = System.getProperties();
		properties.forEach((k, v) -> System.out.println("k: " + k + " v:" + v));
	}
	
	/*
	 * @EnabledIfSystemProperty execute a test if a system property is equals to the
	 * one that we pass in the annotation.
	 * In "matches" we can put a regular expression
	 * Like in the previous examples, we can use @DisabledIfSystemProperty 
	 * for have the oposite logic
	 */
	@Test
	@DisplayName("test_java_version")
	@EnabledIfSystemProperty(named = "java.version", matches="1.8.0_144")
	void testJavaVersion() {
		
	}
	
	/*
	 * test if we pass the environment variable ENV=dev in the
	 * run configuration/arguments -ea -DENV=dev
	 */
	@Test
	@DisplayName("test_dev")
	@EnabledIfSystemProperty(named = "ENV", matches="dev")
	void testDev() {
		
	}
}
