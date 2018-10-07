package com.opalina.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.opalina.model.QuizQuestion;


public interface QuizQuestionRepository extends CrudRepository<QuizQuestion,Long> {
	@Override
	List<QuizQuestion> findAll();
}
