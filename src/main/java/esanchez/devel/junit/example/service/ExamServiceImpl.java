package esanchez.devel.junit.example.service;

import java.util.List;
import java.util.Optional;

import esanchez.devel.junit.example.model.Exam;
import esanchez.devel.junit.example.repository.ExamRepository;
import esanchez.devel.junit.example.repository.QuestionsRepository;

public class ExamServiceImpl implements ExamService{

	private ExamRepository examRepository;
	private QuestionsRepository questionsRepository;
	
	public ExamServiceImpl(ExamRepository examRepository, QuestionsRepository questionsRepository) {
		this.examRepository = examRepository;
		this.questionsRepository = questionsRepository;
	}
	
	@Override
	public Exam findExamByName(String name) {
		Optional<Exam> exam = examRepository.findAll()
				.stream()
				.filter(e -> name.equals(e.getName()))
				.findFirst();
		
		if (exam.isPresent()) {
			return exam.get();
		} else {
			return null;
		}
	}

	@Override
	public Exam findExamWithQuestions(String name) {
		Exam exam = findExamByName(name);
		
		if (exam != null) {
			List<String> questions = questionsRepository.findQuestionsByExamId(exam.getId());
			exam.setQuestions(questions);
		} else {
			return null;
		}
		return exam;
	}
}
