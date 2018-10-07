package com.opalina.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.opalina.model.QuizQuestionType;

public interface QuizQuestionTypeRepository extends CrudRepository<QuizQuestionType,Long> {
	@Override
	List<QuizQuestionType> findAll();
	QuizQuestionType findByType(String type);
}
