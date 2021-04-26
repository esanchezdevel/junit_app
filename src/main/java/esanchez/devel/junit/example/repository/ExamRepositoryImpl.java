package esanchez.devel.junit.example.repository;

import java.util.Arrays;
import java.util.List;

import esanchez.devel.junit.example.model.Exam;

public class ExamRepositoryImpl implements ExamRepository {

	@Override
	public List<Exam> findAll() {
		return Arrays.asList(new Exam(5L, "Maths"), new Exam(6L, "History"), new Exam(7L, "Language"));
	}

}
