package esanchez.devel.junit.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import esanchez.devel.junit.example.model.Exam;
import esanchez.devel.junit.example.repository.ExamRepository;
import esanchez.devel.junit.example.repository.QuestionsRepository;

public class ExamServiceImplTest {

	/*
	 * the class that we will mock
	 */
	private static ExamRepository repository;
	private static QuestionsRepository questionsRepository;
	
	private ExamService service;
	
	@BeforeEach
	void setUpMethod() {
		/*
		 * create a mock instance of ExamRepository
		 */
		repository = mock(ExamRepository.class);
		questionsRepository = mock(QuestionsRepository.class);
		service = new ExamServiceImpl(repository, questionsRepository);
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
}
