package com.opalina.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class QuizQuestionType {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotBlank
	@Column(unique=true)
	private String type;
	@JsonIgnore
	@OneToMany(mappedBy="quizQuestionType",fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	private List<QuizQuestion> quizQuestion;
	
	public QuizQuestionType(){		
	}
	public QuizQuestionType(String type){
		this.type=type;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<QuizQuestion> getQuizQuestion() {
		return quizQuestion;
	}
	public void setQuizQuestion(List<QuizQuestion> quizQuestion) {
		this.quizQuestion = quizQuestion;
	}
	
}
