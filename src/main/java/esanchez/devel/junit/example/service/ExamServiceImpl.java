package esanchez.devel.junit.example.service;

import java.util.Optional;

import esanchez.devel.junit.example.model.Exam;
import esanchez.devel.junit.example.repository.ExamRepository;

public class ExamServiceImpl implements ExamService{

	private ExamRepository examRepository;
	
	public ExamServiceImpl(ExamRepository examRepository) {
		this.examRepository = examRepository;
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

}
