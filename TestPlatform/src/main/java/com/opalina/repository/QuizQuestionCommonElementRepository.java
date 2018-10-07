package com.opalina.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.opalina.model.QuizQuestionCommonElement;

public interface QuizQuestionCommonElementRepository extends CrudRepository<QuizQuestionCommonElement,Long>{
	Optional<QuizQuestionCommonElement> findById(Long id);
	List<QuizQuestionCommonElement> findByGrouped(Integer grouped);
}
