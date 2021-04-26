package esanchez.devel.junit.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import esanchez.devel.junit.example.model.Exam;
import esanchez.devel.junit.example.repository.ExamRepository;

public class ExamServiceImplTest {

	/*
	 * the class that we will mock
	 */
	private static ExamRepository repository;
	
	@BeforeAll
	static void setUp() {
		/*
		 * create a mock instance of ExamRepository
		 */
		repository = mock(ExamRepository.class);
	}
	
	@Test
	void findExamByName() {

		ExamService service = new ExamServiceImpl(repository);
		
		List<Exam> data = Arrays.asList(new Exam(5L, "Maths"), new Exam(6L, "History"), new Exam(7L, "Language"));
		
		/*
		 * when the method findAll is invoked, then will return the fixed data list
		 */
		when(repository.findAll()).thenReturn(data);
		
		Exam exam = service.findExamByName("Maths");
		
		assertNotNull(exam);
		assertEquals(5L, exam.getId());
		assertEquals("Maths", exam.getName());
	}
	
	@Test
	void findExamByNameEmpty() {
		ExamService service = new ExamServiceImpl(repository);
		
		List<Exam> data = Collections.emptyList();
		
		/*
		 * when the method findAll is invoked, then will return the fixed data list
		 */
		when(repository.findAll()).thenReturn(data);
		
		Exam exam = service.findExamByName("Maths");
		
		assertNull(exam);
	}
}
