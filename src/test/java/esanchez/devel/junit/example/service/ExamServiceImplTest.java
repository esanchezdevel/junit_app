package esanchez.devel.junit.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import esanchez.devel.junit.example.model.Exam;
import esanchez.devel.junit.example.repository.ExamRepository;
import esanchez.devel.junit.example.repository.ExamRepositoryImpl;
import esanchez.devel.junit.example.repository.QuestionsRepository;
import esanchez.devel.junit.example.repository.QuestionsRepositoryImpl;

/*
 * Extension needed for be able to use mockito with dependency injection
 */
@ExtendWith(MockitoExtension.class)
public class ExamServiceImplTest {

	/*
	 * the class that we will mock
	 */
	//@Mock
	//private ExamRepository repository;
	//@Mock
	//private QuestionsRepository questionsRepository;
	
	@Mock
	private ExamRepositoryImpl repository;
	
	@Mock
	private QuestionsRepositoryImpl questionsRepository;
	
	@InjectMocks
	private ExamServiceImpl service;
	
	/*
	 * Define the ArgumentCaptor with the annotation
	 */
	@Captor
	ArgumentCaptor<Long> captor;
	
	@BeforeEach
	void setUpMethod() {
		/*
		 * This line is for allow the dependency injection with mocks annotations
		 * It makes the same of the @ExtendWith(MockitoExtension.class) that is 
		 * why is commented
		 */
		//MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testFindExamByName() {		
		/*
		 * when the method findAll is invoked, then will return the fixed data list
		 */
		when(repository.findAll()).thenReturn(Data.DATA);
		
		Exam exam = service.findExamByName("Maths");
		
		assertNotNull(exam);
		assertEquals(5L, exam.getId());
		assertEquals("Maths", exam.getName());
	}
	
	@Test
	void testFindExamByNameEmpty() {
		
		List<Exam> data = Collections.emptyList();
		
		/*
		 * when the method findAll is invoked, then will return the fixed data list
		 */
		when(repository.findAll()).thenReturn(data);
		
		Exam exam = service.findExamByName("Maths");
		
		assertNull(exam);
	}
	
	@Test
	void testExamQuestions() {
		when(repository.findAll()).thenReturn(Data.DATA);
		when(questionsRepository.findQuestionsByExamId(anyLong())).thenReturn(Data.QUESTIONS);
		
		Exam exam = service.findExamWithQuestions("Maths");
		
		assertNotNull(exam);
		assertEquals(5, exam.getQuestions().size());
		assertTrue(exam.getQuestions().contains("arithmetics"));
	}
	
	/*
	 * verify is used for check that a method was invoked
	 */
	@Test
	void testExamQuestionsVerify() {
		when(repository.findAll()).thenReturn(Data.DATA);
		when(questionsRepository.findQuestionsByExamId(anyLong())).thenReturn(Data.QUESTIONS);
		
		Exam exam = service.findExamWithQuestions("History");
		
		assertNotNull(exam);
		assertEquals(5, exam.getQuestions().size());
		assertTrue(exam.getQuestions().contains("arithmetics"));
		verify(repository).findAll();
		verify(questionsRepository).findQuestionsByExamId(anyLong());
	}
	
	@Test
	void testSaveExam() {
		
		Exam newExam = Data.EXAM;
		newExam.setQuestions(Data.QUESTIONS);
		
		/*
		 * with .then and adding new Answer with an anonymous function we can increment the id 
		 * programmatically in order to have a more realistic test 
		 */
		when(repository.saveExam(any(Exam.class))).then(new Answer<Exam>() {
			
			Long sequence = 8L;
			
			@Override
			public Exam answer(InvocationOnMock invocation) throws Throwable {
				
				Exam exam = invocation.getArgument(0);
				exam.setId(sequence++);
				return exam;
			}
			
		});
		Exam exam = service.save(newExam);
		
		assertNotNull(exam.getId());
		assertEquals(8L, exam.getId());
		assertEquals("History", exam.getName());
		verify(repository).saveExam(any(Exam.class));
		verify(questionsRepository).saveQuestions(anyList());
	}
	
	@Test
	void testExceptionsManagement() {
		when(repository.findAll()).thenReturn(Data.DATA);
		when(questionsRepository.findQuestionsByExamId(anyLong())).thenThrow(IllegalArgumentException.class);
		assertThrows(IllegalArgumentException.class, () -> {
			service.findExamWithQuestions("Maths");
		});
		verify(questionsRepository).findQuestionsByExamId(anyLong());
	}
	
	@Test
	void testExceptionsManagementWithNulls() {
		when(repository.findAll()).thenReturn(Data.EXAMS_ID_NULL);
		when(questionsRepository.findQuestionsByExamId(isNull())).thenThrow(IllegalArgumentException.class);
		assertThrows(IllegalArgumentException.class, () -> {
			service.findExamWithQuestions("Maths");
		});
		verify(questionsRepository).findQuestionsByExamId(isNull());
	}
	
	@Test
	void testArgumentMatchers() {
		when(repository.findAll()).thenReturn(Data.DATA);
		when(questionsRepository.findQuestionsByExamId(anyLong())).thenReturn(Data.QUESTIONS);

		service.findExamWithQuestions("Maths");
		
		verify(repository).findAll();
		/*
		 * With argThat we can check with a lambda expression that an argument passed to the method
		 * that we are verifying is the expected one
		 */
		verify(questionsRepository).findQuestionsByExamId(argThat(arg -> arg != null && arg.equals(5L)));
		/*
		 * A different way to test that the value is equals to the one expected
		 */
		verify(questionsRepository).findQuestionsByExamId(eq(5L));
		verify(questionsRepository).findQuestionsByExamId(argThat(arg -> arg != null && arg >= 5L));
	}
	
	
	@Test
	void testMyArgumentMatchers() {
		when(repository.findAll()).thenReturn(Data.DATA_WITH_NEGATIVES);
		when(questionsRepository.findQuestionsByExamId(anyLong())).thenReturn(Data.QUESTIONS);

		service.findExamWithQuestions("Maths");
		
		verify(repository).findAll();
		/*
		 * With argThat we can check with a lambda expression that an argument passed to the method
		 * that we are verifying is the expected one
		 */
		verify(questionsRepository).findQuestionsByExamId(argThat(arg -> arg != null && arg.equals(-5L)));
		/*
		 * A different way to test that the value is equals to the one expected
		 */
		verify(questionsRepository).findQuestionsByExamId(eq(-5L));
		
		/*
		 * test our custom Argument Matcher. Will fail because we are passing a negative argument
		 */
		verify(questionsRepository).findQuestionsByExamId(argThat(new MyArgsMatchers()));
	}
	
	
	/*
	 * Our own argument matcher
	 */
	public static class MyArgsMatchers implements ArgumentMatcher<Long> {

		private Long argument;
		
		@Override
		public boolean matches(Long argument) {
			this.argument = argument;
			return argument != null && argument > 0;
		}

		@Override
		public String toString() {
			return "Is a custom error message when the test fails. " + this.argument + " must be a positive integer number";
		}
	}
	
	
	@Test
	void testArgumentCaptor() {
		when(repository.findAll()).thenReturn(Data.DATA);
		when(questionsRepository.findQuestionsByExamId(anyLong())).thenReturn(Data.QUESTIONS);

		service.findExamWithQuestions("Maths");
		
		/*
		 * The argumentCaptor capture the argument passed in the mocked method
		 */
		ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
		verify(questionsRepository).findQuestionsByExamId(captor.capture());
		
		/*
		 * assert that the value captured is the expected one
		 */
		assertEquals(5L, captor.getValue());
	}
	
	
	@Test
	void testArgumentCaptor2() {
		when(repository.findAll()).thenReturn(Data.DATA);
		when(questionsRepository.findQuestionsByExamId(anyLong())).thenReturn(Data.QUESTIONS);

		service.findExamWithQuestions("Maths");
		
		/*
		 * The argumentCaptor capture the argument passed in the mocked method
		 * this time the captor is defined with an annotation at the beginnig of the class
		 */
		verify(questionsRepository).findQuestionsByExamId(captor.capture());
		
		/*
		 * assert that the value captured is the expected one
		 */
		assertEquals(5L, captor.getValue());
	}
	
	@Test
	void testDoCallRealMethod() {
		when(repository.findAll()).thenReturn(Data.DATA);
		//when(questionsRepository.findQuestionsByExamId(anyLong())).thenReturn(Data.QUESTIONS);
		
		doCallRealMethod().when(questionsRepository).findQuestionsByExamId(anyLong());
		Exam exam = service.findExamWithQuestions("Maths");
		
		assertEquals(5L, exam.getId());
		assertEquals("Maths", exam.getName());
	}
	
	@Test
	void testSpy() {
		/*
		 * with spy we will use real methods, so we can't create an spy with an abstract
		 * class or and interface. this is why we use the ExamRepositoryImpl, because is the
		 * real implementation
		 */
		ExamRepository repository = spy(ExamRepositoryImpl.class);
		QuestionsRepository questionRepository = spy(QuestionsRepositoryImpl.class);
		ExamService examService = new ExamServiceImpl(repository, questionRepository);
		
		/*
		 * when we use an spy, if we want to avoid that the real method is call and use 
		 * the mock one, we have to make the when.thenReturn in the other direction
		 * first doReturn and after that the when
		 */
		doReturn(Data.QUESTIONS).when(questionRepository).findQuestionsByExamId(anyLong());

		Exam exam = examService.findExamWithQuestions("Maths");
		
		assertEquals(5L, exam.getId());
		assertEquals("Maths", exam.getName());
		assertEquals(5, exam.getQuestions().size());
		assertTrue(exam.getQuestions().contains("arithmetics"));
		
		/*
		 * veriy for check that the methods are invoked
		 */
		verify(repository).findAll();
		verify(questionRepository).findQuestionsByExamId(anyLong());
	}
	
	@Test
	void testInvocationsOrder() {
		
		when(repository.findAll()).thenReturn(Data.DATA);
		
		service.findExamWithQuestions("Maths");
		
		service.findExamWithQuestions("Language");
		
		/*
		 * with inOrder we can verify that the methods are invoked in the
		 * correct order
		 */
		InOrder inOrder = inOrder(questionsRepository);
		inOrder.verify(questionsRepository).findQuestionsByExamId(5L);
		inOrder.verify(questionsRepository).findQuestionsByExamId(7L);
		
	}
	
	
	@Test
	void testInvocationsOrder2() {
		
		when(repository.findAll()).thenReturn(Data.DATA);
		
		service.findExamWithQuestions("Maths");
		
		service.findExamWithQuestions("Language");
		
		InOrder inOrder = inOrder(repository, questionsRepository);
		inOrder.verify(repository).findAll();
		inOrder.verify(questionsRepository).findQuestionsByExamId(5L);
		inOrder.verify(repository).findAll();
		inOrder.verify(questionsRepository).findQuestionsByExamId(7L);
		
	}
	
	@Test
	void testnumberOfInvocations() {
		
		when(repository.findAll()).thenReturn(Data.DATA);
		
		service.findExamWithQuestions("Maths");

		/*
		 * passing times(1) in the second argument of the verify we can validate
		 * that the method is invoked 1 time
		 */
		verify(questionsRepository, times(1)).findQuestionsByExamId(5L);
		
		/*
		 * passing atLeast(1) in the second argument of the verify we can validate
		 * that the method is invoked at least 1 time
		 */
		verify(questionsRepository, atLeast(1)).findQuestionsByExamId(5L);		
		
		/*
		 * passing atLeastOnce() in the second argument of the verify we can validate
		 * that the method is invoked at least 1 time
		 */
		verify(questionsRepository, atLeastOnce()).findQuestionsByExamId(5L);
		
		/*
		 * passing atMost(10) in the second argument of the verify we can validate
		 * that the method is invoked at most 10 times
		 */
		verify(questionsRepository, atMost(10)).findQuestionsByExamId(5L);
		
		/*
		 * passing atMostOnce() in the second argument of the verify we can validate
		 * that the method is invoked at most 1 time
		 */
		verify(questionsRepository, atMostOnce()).findQuestionsByExamId(5L);
	}
}
