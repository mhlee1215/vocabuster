package org.wordbuster.domain;

public class VBWordQuizVO {
	private String userid;
	//문제 불러올때 사용
	private Integer selectionCount;		//보기 수
	private Integer questionCount;		//퀴즈 번호
	private String questionCategory;	//퀴즈 카테고리
	private String quizType;			//퀴즈 타입	..일단 단어 to 의미 
	
	//정답 제출할때 사용
	private String quizWordName;		//퀴즈 단어
	private String isCorrect;			//퀴즈 정답여부
	
	private String fromIndex;			//검색 시작 인덱스
	private String toIndex;				//검색 종료 인덱스
	
	private String questionFromIndex;			//검색 시작 인덱스
	private String questionToIndex;				//검색 종료 인덱스
	
	private String stageIndex;			//퀴즈 스테이지 인덱스
	private String stageMaxIndex;		//퀴즈 맥스 인덱스
	
	/**
	 * 정렬 스트링
	 */
	private String searchOrderString;
	
	
	public String getStageIndex() {
		return stageIndex;
	}
	public void setStageIndex(String stageIndex) {
		this.stageIndex = stageIndex;
	}
	public String getStageMaxIndex() {
		return stageMaxIndex;
	}
	public void setStageMaxIndex(String stageMaxIndex) {
		this.stageMaxIndex = stageMaxIndex;
	}
	public String getQuestionFromIndex() {
		return questionFromIndex;
	}
	public void setQuestionFromIndex(String questionFromIndex) {
		this.questionFromIndex = questionFromIndex;
	}
	public String getQuestionToIndex() {
		return questionToIndex;
	}
	public void setQuestionToIndex(String questionToIndex) {
		this.questionToIndex = questionToIndex;
	}
	public String getQuestionCategory() {
		return questionCategory;
	}
	public void setQuestionCategory(String questionCategory) {
		this.questionCategory = questionCategory;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getSearchOrderString() {
		return searchOrderString;
	}
	public void setSearchOrderString(String searchOrderString) {
		this.searchOrderString = searchOrderString;
	}
	public String getFromIndex() {
		return fromIndex;
	}
	public void setFromIndex(String fromIndex) {
		this.fromIndex = fromIndex;
	}
	public String getToIndex() {
		return toIndex;
	}
	public void setToIndex(String toIndex) {
		this.toIndex = toIndex;
	}
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
		return "#####VBWordQuizVO#####\nfromIndex=" + fromIndex
				+ "\nisCorrect=" + isCorrect + "\nquestionCategory="
				+ questionCategory + "\nquestionCount=" + questionCount
				+ "\nquestionFromIndex=" + questionFromIndex
				+ "\nquestionToIndex=" + questionToIndex + "\nquizType="
				+ quizType + "\nquizWordName=" + quizWordName
				+ "\nsearchOrderString=" + searchOrderString
				+ "\nselectionCount=" + selectionCount + "\nstageIndex="
				+ stageIndex + "\nstageMaxIndex=" + stageMaxIndex
				+ "\ntoIndex=" + toIndex + "\nuserid=" + userid
				+ "\n##############################";
	}
	
	public void initVO(){
		selectionCount = 4;
		questionCount = 0;
	}
	
	
	
}
