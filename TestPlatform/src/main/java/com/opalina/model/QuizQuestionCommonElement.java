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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class QuizQuestionCommonElement {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotNull
	private Integer grouped;
	@NotNull
	private Integer imageOrParagraph;
	@NotBlank
	@Column(columnDefinition="TEXT")
	private String text;
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinTable(name="question_join_table",
	joinColumns= {@JoinColumn(name="quiz_question_common_element_id",referencedColumnName="id")},
	inverseJoinColumns= {@JoinColumn(name="quiz_question_id",referencedColumnName="id")})
	private List<QuizQuestion> quizQuestion;
	
	public QuizQuestionCommonElement() {
	}	
	public QuizQuestionCommonElement(Integer imageOrParagraph,String text,Integer grouped) {
		this.imageOrParagraph=imageOrParagraph;
		this.text=text;
		this.grouped=grouped;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getImageOrParagraph() {
		return imageOrParagraph;
	}
	public void setImageOrParagraph(Integer imageOrParagraph) {
		this.imageOrParagraph = imageOrParagraph;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<QuizQuestion> getQuizQuestion() {
		return quizQuestion;
	}
	public void setQuizQuestion(List<QuizQuestion> quizQuestion) {
		this.quizQuestion = quizQuestion;
	}
	public Integer getGrouped() {
		return grouped;
	}
	public void setGrouped(Integer grouped) {
		this.grouped = grouped;
	}

}
