package org.wordbuster.domain;

public class VBWordQuizVO {
	//문제 불러올때 사용
	private Integer selectionCount;		//보기 수
	private Integer questionCount;		//퀴즈 번호
	private String quizType;			//퀴즈 타입	..일단 단어 to 의미 
	
	//정답 제출할때 사용
	private String quizWordName;		//퀴즈 단어
	private String isCorrect;			//퀴즈 정답여부
	
	public Integer getQuestionCount() {
		return questionCount;
	}
	public void setQuestionCount(Integer questionCount) {
		this.questionCount = questionCount;
	}
	public String getQuizType() {
		return quizType;
	}
	public void setQuizType(String quizType) {
		this.quizType = quizType;
	}
	public Integer getSelectionCount() {
		return selectionCount;
	}
	public void setSelectionCount(Integer selectionCount) {
		this.selectionCount = selectionCount;
	}
	public String getQuizWordName() {
		return quizWordName;
	}
	public void setQuizWordName(String quizWordName) {
		this.quizWordName = quizWordName;
	}
	public String getIsCorrect() {
		return isCorrect;
	}
	public void setIsCorrect(String isCorrect) {
		this.isCorrect = isCorrect;
	}
	@Override
	public String toString() {
		return "VBWordQuizVO [isCorrect=" + isCorrect + ", quizWordName="
				+ quizWordName + ", selectionCount=" + selectionCount + "]";
	}
	
	public void initVO(){
		selectionCount = 4;
		questionCount = 0;
	}
	
	
	
}
