package esanchez.devel.junit.example.repository;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import esanchez.devel.junit.example.model.Exam;
import esanchez.devel.junit.example.service.Data;

public class ExamRepositoryImpl implements ExamRepository {

	@Override
	public List<Exam> findAll() {
		System.out.println("ExamRepositoryImpl.findAll");
		try {
			System.out.println("Exam Repository Impl");
			TimeUnit.SECONDS.sleep(5);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		//return Arrays.asList(new Exam(5L, "Maths"), new Exam(6L, "History"), new Exam(7L, "Language"));
		return Data.DATA;
	}

	@Override
	public Exam saveExam(Exam exam) {
		System.out.println("ExamRepositoryImpl.saveExam");
		return Data.EXAM;
	}

}
