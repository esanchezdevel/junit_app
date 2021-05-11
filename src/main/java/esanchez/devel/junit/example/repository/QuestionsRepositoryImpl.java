package esanchez.devel.junit.example.repository;

import java.util.List;

import esanchez.devel.junit.example.service.Data;

public class QuestionsRepositoryImpl implements QuestionsRepository {

	@Override
	public List<String> findQuestionsByExamId(Long id) {
		System.out.println("QuestionsRepositoryImpl.findQuestionsByExamId");
		return Data.QUESTIONS;
	}

	@Override
	public void saveQuestions(List<String> questions) {
		System.out.println("QuestionsRepositoryImpl.saveQuestions");
	}

}
