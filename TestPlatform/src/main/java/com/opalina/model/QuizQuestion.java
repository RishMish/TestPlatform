package com.opalina.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class QuizQuestion implements Cloneable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotBlank
	@Column(columnDefinition="TEXT")
	private String question;
	@NotNull
	private Integer questionDifficulty;
	@NotNull
	private Integer questionFormat;
	@ManyToOne
	private QuizQuestionType quizQuestionType;
	@NotNull
	private Integer singleOrMulti;
	@ManyToOne
	@JoinTable(name="question_join_table",
	joinColumns={@JoinColumn(name="quiz_question_id",referencedColumnName="id")},
	inverseJoinColumns={@JoinColumn(name="quiz_question_common_element_id",referencedColumnName="id")})
	private QuizQuestionCommonElement quizQuestionCommonElement;
	@JsonIgnore
	@OneToMany(mappedBy="quizQuestion",fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	private List<QuizQuestionOption> quizQuestionOption;
	
	public QuizQuestion() {	
	}
	public QuizQuestion(Integer questionDifficulty,Integer questionFormat,String question) {
		this.questionDifficulty=questionDifficulty;
		this.questionFormat=questionFormat;
		this.question=question;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<QuizQuestionOption> getQuizQuestionOption() {
		return quizQuestionOption;
	}
	public void setQuizQuestionOption(List<QuizQuestionOption> quizQuestionOption) {
		this.quizQuestionOption = quizQuestionOption;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public QuizQuestionType getQuizQuestionType() {
		return quizQuestionType;
	}
	public void setQuizQuestionType(QuizQuestionType quizQuestionType) {
		this.quizQuestionType = quizQuestionType;
	}
	public Integer getQuestionDifficulty() {
		return questionDifficulty;
	}
	public void setQuestionDifficulty(Integer questionDifficulty) {
		this.questionDifficulty = questionDifficulty;
	}
	public Integer getQuestionFormat() {
		return questionFormat;
	}
	public void setQuestionFormat(Integer questionFormat) {
		this.questionFormat = questionFormat;
	}
	public QuizQuestionCommonElement getQuizQuestionCommonElement() {
		return quizQuestionCommonElement;
	}
	public void setQuizQuestionCommonElement(QuizQuestionCommonElement quizQuestionCommonElement) {
		this.quizQuestionCommonElement = quizQuestionCommonElement;
	}
	public Integer getSingleOrMulti() {
		return singleOrMulti;
	}
	public void setSingleOrMulti(Integer singleOrMulti) {
		this.singleOrMulti = singleOrMulti;
	}
}
