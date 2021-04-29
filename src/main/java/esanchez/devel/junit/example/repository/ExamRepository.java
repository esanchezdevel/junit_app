package esanchez.devel.junit.example.repository;

import java.util.List;

import esanchez.devel.junit.example.model.Exam;

public interface ExamRepository {

	List<Exam> findAll();
	
	Exam saveExam(Exam exam);
}
