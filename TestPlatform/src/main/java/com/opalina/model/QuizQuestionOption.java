package com.opalina.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class QuizQuestionOption {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotBlank
	@Column(columnDefinition="TEXT")
	private String option;
	@NotNull
	private String isRight;
	@ManyToOne
	private QuizQuestion quizQuestion;
	
	public QuizQuestionOption() {		
	}
	public QuizQuestionOption(String option,String isRight) {
		this.option=option;
		this.isRight=isRight;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public String getIsRight() {
		return isRight;
	}
	public void setIsRight(String isRight) {
		this.isRight = isRight;
	}
	public QuizQuestion getQuizQuestion() {
		return quizQuestion;
	}
	public void setQuizQuestion(QuizQuestion quizQuestion) {
		this.quizQuestion = quizQuestion;
	}
}
