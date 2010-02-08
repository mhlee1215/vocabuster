package org.wordbuster.domain;

import java.util.List;
import java.util.Set;

public class VBWord {
	public String wordName;
	
	/**
	 * 관련 이미지
	 */
	private String imageUrl;
	
	/**
	 * 발음 태그 HTML
	 */
	private String soundHtml;
	
	/**
	 * 발음기호
	 */
	private String soundSymbol;
	
	/**
	 * 입력수
	 */
	private Integer insertedCount;
	
	/**
	 * 문제로 사용 수
	 */
	private Integer solvedCount;

	/**
	 * 단어 언어
	 */
	private String wordLanguage;
	/**
	 * 뜻 언어
	 */
	private String meaningLanauge;

	/**
	 * 단어 뜻
	 */
	private List<VBWordInfo> wordInfoList;
	
	
	public List<VBWordInfo> getWordInfoList() {
		return wordInfoList;
	}

	public void setWordInfoList(List<VBWordInfo> wordInfoList) {
		this.wordInfoList = wordInfoList;
	}

	public VBWord(){
		init();
	}
	
	public String getSoundSymbol() {
		return soundSymbol;
	}

	public void setSoundSymbol(String soundSymbol) {
		this.soundSymbol = soundSymbol;
	}

	public void init(){
		if(insertedCount == null) insertedCount = 1;
		if(solvedCount == null) solvedCount = 0;
	}
	
	public Integer getSolvedCount() {
		return solvedCount;
	}

	public void setSolvedCount(Integer solvedCount) {
		this.solvedCount = solvedCount;
	}

	public String getWordLanguage() {
		return wordLanguage;
	}

	public void setWordLanguage(String wordLanguage) {
		this.wordLanguage = wordLanguage;
	}

	public String getMeaningLanauge() {
		return meaningLanauge;
	}

	public void setMeaningLanauge(String meaningLanauge) {
		this.meaningLanauge = meaningLanauge;
	}

	public String getSoundHtml() {
		return soundHtml;
	}

	public void setSoundHtml(String soundHtml) {
		this.soundHtml = soundHtml;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getInsertedCount() {
		return insertedCount;
	}

	public void setInsertedCount(Integer insertedCount) {
		this.insertedCount = insertedCount;
	}

	public String getWordName() {
		return wordName;
	}

	public void setWordName(String wordName) {
		this.wordName = wordName;
	}

    public void increaseInsertedCount(){
    	if(insertedCount == null) insertedCount = 1;
    	else insertedCount++;
    }
    
    public void increaseSolvedCount(){
    	init();
    	solvedCount++;
    }

	@Override
	public String toString() {
		return "VBWord [insertedCount=" + insertedCount + ", wordName="
				+ wordName + "]";
	}
    
}
