package org.wordbuster.domain;

import java.util.List;
import java.util.Set;

import com.google.appengine.api.datastore.Text;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class VBWord {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	public String wordName;
	
	/**
	 * 사용 wordmap key 리스트
	 */
	@Persistent//(mappedBy = "word")
	private Set<Key> wordMapKey;//VBWordMap wordMap;
	
	/**
	 * 뜻 리스트
	 */
	@Persistent(mappedBy = "word")
	private List<VBWordInfo> wordInfoList;
	
	/**
	 * 카테고리 리스트
	 */
	@Persistent(mappedBy = "word")
	private List<VBCategory> CategoryList;
	
	/**
	 * 관련 이미지
	 */
	@Persistent
	private String imageUrl;
	
	/**
	 * 발음 태그 HTML
	 */
	@Persistent
	private Text soundHtml;
	
	/**
	 * 발음기호
	 */
	private String soundSymbol;
	
	/**
	 * 입력수
	 */
	@Persistent
	private Integer insertedCount;
	
	/**
	 * 문제로 사용 수
	 */
	@Persistent
	private Integer solvedCount;

	/**
	 * 단어 언어
	 */
	@Persistent
	private String wordLanguage;
	/**
	 * 뜻 언어
	 */
	@Persistent
	private String meaningLanauge;
	
	
	public String getSoundSymbol() {
		return soundSymbol;
	}

	public void setSoundSymbol(String soundSymbol) {
		this.soundSymbol = soundSymbol;
	}

	public void init(){
		if(insertedCount == null) insertedCount = 0;
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

	public Text getSoundHtml() {
		return soundHtml;
	}

	public void setSoundHtml(Text soundHtml) {
		this.soundHtml = soundHtml;
	}

	public Set<Key> getWordMapKey() {
		return wordMapKey;
	}

	public void setWordMapKey(Set<Key> wordMapKey) {
		this.wordMapKey = wordMapKey;
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

	public Key getKey() {
		return key;
	}
	
	public void setKey(Key key) {
		this.key = key;
	}
	
	public String getWordName() {
		return wordName;
	}

	public void setWordName(String wordName) {
		this.wordName = wordName;
	}

	public List<VBWordInfo> getWordInfoList() {
		return wordInfoList;
	}

	public void setWordInfoList(List<VBWordInfo> wordInfoList) {
		this.wordInfoList = wordInfoList;
	}

    public List<VBCategory> getCategoryList() {
        return CategoryList;
    }

    public void setCategoryList(List<VBCategory> categoryList) {
        CategoryList = categoryList;
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
