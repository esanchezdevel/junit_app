package esanchez.devel.junit.example.repository;

import java.util.Arrays;
import java.util.List;

public class QuestionsRepositoryImpl implements QuestionsRepository {

	private static final List<String> QUESTIONS = Arrays.asList(
			"arithmetics", 
			"integrals", 
			"derived", 
			"trigonometry", 
			"geometry");
	
	@Override
	public List<String> findQuestionsByExamId(Long id) {
		return QUESTIONS;
	}

	@Override
	public void saveQuestions(List<String> questions) {
		// TODO Auto-generated method stub
		
	}

}
