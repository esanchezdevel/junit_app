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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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
public class ExamServiceImplSpyTest {
	
	@Spy
	private ExamRepositoryImpl repository;
	
	@Spy
	private QuestionsRepositoryImpl questionsRepository;
	
	@InjectMocks
	private ExamServiceImpl service;
	
	@Test
	void testSpy() {
		/*
		 * with spy we will use real methods, so we can't create an spy with an abstract
		 * class or and interface. this is why we use the ExamRepositoryImpl, because is the
		 * real implementation
		 */
		
		/*
		 * when we use an spy, if we want to avoid that the real method is call and use 
		 * the mock one, we have to make the when.thenReturn in the other direction
		 * first doReturn and after that the when
		 */
		doReturn(Data.QUESTIONS).when(questionsRepository).findQuestionsByExamId(anyLong());

		Exam exam = service.findExamWithQuestions("Maths");
		
		assertEquals(5L, exam.getId());
		assertEquals("Maths", exam.getName());
		assertEquals(5, exam.getQuestions().size());
		assertTrue(exam.getQuestions().contains("arithmetics"));
		
		/*
		 * veriy for check that the methods are invoked
		 */
		verify(repository).findAll();
		verify(questionsRepository).findQuestionsByExamId(anyLong());
	}
}
