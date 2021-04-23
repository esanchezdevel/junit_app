package esanchez.devel.junit.example.model;

import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class TimeoutTest {

	@Nested
	@Tag("timeout")
	class TheTimeoutTests {
		
		
		/*
		 * @Timeout for set a timeout limit of execution in the test
		 */
		@Test
		@DisplayName("test_timeout")
		@Timeout(5)
		void testTimeout() throws InterruptedException {
			TimeUnit.SECONDS.sleep(4);
		}
		
		/*
		 * @Timeout using different timeunit. In this case miliseconds
		 */
		@Test
		@DisplayName("test_timeout_milliseconds")
		@Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
		void testTimeoutMilliseconds() throws InterruptedException {
			TimeUnit.SECONDS.sleep(4);
		}
		
		/*
		 * With assertTimeout we can make the same test using a lambda expresion
		 */
		@Test
		@DisplayName("test_assert_timeout")
		void testAssertTimeout() throws InterruptedException {
			assertTimeout(Duration.ofMillis(5000), () -> TimeUnit.SECONDS.sleep(4));
		}
	}
}
