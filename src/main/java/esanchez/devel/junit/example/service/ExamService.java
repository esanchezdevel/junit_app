package esanchez.devel.junit.example.service;

import esanchez.devel.junit.example.model.Exam;

public interface ExamService {

	Exam findExamByName(String name);
	
	Exam findExamWithQuestions(String name);
	
	Exam save(Exam exam);
}
