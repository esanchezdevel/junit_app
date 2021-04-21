package esanchez.devel.junit.example.model;

/*
 * static import for be able to use all assertions methods
 * without writing the Assertions class every time
 */
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperties;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

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
	 * @Nested is an annotation used for have a class inside the main class
	 * for have the tests more organized
	 */
	@Nested
	@DisplayName("test_operative_systems")
	class OperativeSystemsTest {
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
	
	@Test
	@Disabled
	@DisplayName("test_get_env_variables")
	void testPrintEnvironmentVariables() {
		Map<String, String> variables = System.getenv();
		variables.entrySet().stream().forEach(e -> System.out.println("k: " + e.getKey() + " v: " + e.getValue()));
	}
	
	@Test
	@DisplayName("test_session_desktop")
	@EnabledIfEnvironmentVariable(named="XDG_SESSION_DESKTOP", matches="ubuntu")
	void testSessionDesktop() {
		
	}
	
	/*
	 * test if we pass the environment variable ENVIRONMENT=dev in the
	 * run configuration/environment
	 */
	@Test
	@DisplayName("test_env")
	@EnabledIfEnvironmentVariable(named = "ENVIRONMENT", matches="dev")
	void testEnv() {
		
	}
	
	@Test
	@DisplayName("test_account_balance_dev")
	void testAccountBalanceDev() {
		boolean isDev = "dev".equals(System.getProperty(("ENV")));
		/*
		 * with "assumeTrue" if isDev is false, then the rest of the test will no execute
		 * but without error.
		 */
		assumeTrue(isDev);
		
		assertEquals(1000.12344, this.account.getBalance().doubleValue());
		/*
		 * assertFalse for check that one evaluation is false
		 * compareTo returns: -1 if <, 0 if ==, 1 if >  
		 */
		assertFalse(account.getBalance().compareTo(BigDecimal.ZERO) < 0);
		assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
	}
	
	
	@Test
	@DisplayName("test_account_balance_dev2")
	void testAccountBalanceDev2() {
		boolean isDev = "dev".equals(System.getProperty(("ENV")));
		/*
		 * with "assumingThat" if isDev is false, we can enclose in a lambda expression
		 * the code that we want to execute if it's true. And if not then that code will not execute
		 * but the test will be passed without errors. 
		 * And we can have more assumingThat with more asserts inside independent each other
		 */
		assumingThat(isDev, () -> {
			assertEquals(1000.12344, this.account.getBalance().doubleValue());
			/*
			 * assertFalse for check that one evaluation is false
			 * compareTo returns: -1 if <, 0 if ==, 1 if >  
			 */
			assertFalse(account.getBalance().compareTo(BigDecimal.ZERO) < 0);
			assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
		});
	}
	
	/*
	 * @RepeatedTest is an annotation for execute the test n times.
	 * RepetitionInfo parameter is optional for get info about the current repetition
	 */
	@RepeatedTest(value=5, name="Repeat number {currentRepetition} of {totalRepetitions}")
	@DisplayName("test_account_name_constructor2") //the test name showed in the display
	void testAccountNameConstructor2(RepetitionInfo info) {
		
		if (info.getCurrentRepetition() > 3)
			System.out.println("executing repetition > 3");
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
	
	
	@Nested
	class ParameterizedTests {
		/*
		 * @ParameterizedTest indicates that the test will be repetead
		 * @ValueSource is where we set all the values that will change in each test
		 * and also is the number of times that the test will be repeated
		 * -Its necessary to pass the parameter in the method with the same type 
		 * of the valueSource data. in this case String.
		 */
		@ParameterizedTest(name="repetition number {index} executing with value {0}") //{0} print the value of the valueSource
		@ValueSource(strings = {"100", "200", "300", "500", "700", "1000"})
		@DisplayName("test_parameterized_valueSource")
		void testParameterizedValueSource(String amount) {
			account.debit(new BigDecimal(amount));
			/*
			 * assertNotNull for check that an object is not null
			 */
			assertNotNull(account.getBalance());
			assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
		}
		
		/*
		 * csvSource is a different way to make a parameterizedTests, using an index and a value
		 */
		@ParameterizedTest(name="repetition number {index} executing with value {0}") //{0} print the value of the valueSource
		@CsvSource({"1,100", "2,200", "3,300", "4,500", "5,700", "6,1000"})
		@DisplayName("test_parameterized_csvSource")
		void testParameterizedCsvSource(String index, String amount) {
			
			System.out.println(index + " -> " + amount);
			account.debit(new BigDecimal(amount));
			/*
			 * assertNotNull for check that an object is not null
			 */
			assertNotNull(account.getBalance());
			assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
		}
	}
	
	/*
	 * MethodSource is a different way to make a parameterizedTests, using a method that returns the list
	 * with our values
	 */
	@ParameterizedTest(name="repetition number {index} executing with value {0}") //{0} print the value of the valueSource
	@MethodSource("amounts")
	@DisplayName("test_parameterized_methodSource")
	void testParameterizedMethodSource(String amount) {
		account.debit(new BigDecimal(amount));
		/*
		 * assertNotNull for check that an object is not null
		 */
		assertNotNull(account.getBalance());
		assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
	}
	
	static List<String> amounts() {
		return Arrays.asList("100", "200", "300", "500", "700", "1000");
	}
}
