package esanchez.devel.junit.example.repository;

import java.util.List;

public interface QuestionsRepository {

	List<String> findQuestionsByExamId(Long id);
	
	void saveQuestions(List<String> questions);
}
