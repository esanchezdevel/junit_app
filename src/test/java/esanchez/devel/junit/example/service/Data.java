package esanchez.devel.junit.example.service;

import java.util.Arrays;
import java.util.List;

import esanchez.devel.junit.example.model.Exam;

public class Data {
	public static final List<Exam> DATA = Arrays.asList(
			new Exam(5L, "Maths"), 
			new Exam(6L, "History"), 
			new Exam(7L, "Language"));
	
	public static final List<Exam> EXAMS_ID_NULL = Arrays.asList(
			new Exam(null, "Maths"), 
			new Exam(null, "History"), 
			new Exam(null, "Language"));
	
	public static final List<String> QUESTIONS = Arrays.asList(
			"arithmetics", 
			"integrals", 
			"derived", 
			"trigonometry", 
			"geometry");
	
	public static final Exam EXAM = new Exam(null, "History");
}
