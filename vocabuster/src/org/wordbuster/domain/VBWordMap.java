package org.wordbuster.domain;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class VBWordMap {
	private final int MAX_DELAY_COUNT = 5;
	private final int ANSWER_WEIGHT = 1;
	private final int WRONG_WEIGHT = 5;
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key Primarykey;
	
    @Persistent
    private Key key;
	
	@Persistent
	private String wordName;

	@Persistent//(mappedBy = "wordMap")
	private Key userKey;
	
	@Persistent//(mappedBy = "wordMap")
	private Key wordKey;

	@Persistent
	private Integer score;
	
	@Persistent
	private Integer insertCount;
	
	/**
	 * 문제를 맞춘 직후에 올라가고, 그 뒤선택 될때마다 감소되며, 0이 되었을때 다시 선택 될 수 있다.
	 * 같은 문제가 반복되는것을 방지하기 위함
	 */
	@Persistent
	private Integer delayCount;
	
	@NotPersistent
	private VBWord word;
	
	
	
	/**
	 * 맞춘 횟수
	 */
	@Persistent
	private Integer answerCount;
	
	/**
	 * 틀린 횟수
	 */
	@Persistent
	private Integer wrongCount;
	
	/**
	 * 총 횟수 
	 */
	@Persistent
	private Integer totalCount;
	
	/**
	 * 정답률
	 */
	@Persistent
	private Float answerRate;
	
	/**
	 * 오답률
	 */
	@Persistent
	private Float wrongRate;
	
	public void init(){
		if(score == null) score = 0;
		if(answerCount == null) answerCount = 0;
		if(wrongCount == null) wrongCount = 0;
		if(totalCount == null) totalCount = 0;
		if(delayCount == null) delayCount = 0;
		if(answerRate == null) answerRate = 0.0f;
		if(wrongRate == null) wrongRate = 0.0f;
	}
	
	public void wrong(){
		init();
		score-=WRONG_WEIGHT;
		wrongCount++;
		totalCount++;
		wrongRate = (Float)((float)wrongCount/totalCount);
	}
	public void correct(){
		init();
		score+=ANSWER_WEIGHT;
		answerCount++;
		totalCount++;
		answerRate = (Float)((float)answerCount/totalCount);
	}
	public Integer getScore() {
		init();
		return score;
	}
	public void increaseInsertCount(){
		if(insertCount == null) insertCount = 0;
		insertCount++;
	}
	public void setDelay(){
		this.delayCount = MAX_DELAY_COUNT;
	}
	public void decreaseDelay(){
		init();
		this.delayCount--;
	}
	public boolean isPossibleToSelect(){
		init();
		if(delayCount == 0)
			return true;
		return false;
	}
	
	public static Key createKey(User user, String word){
		return KeyFactory.createKey(VBWordMap.class.getSimpleName(), word+user.getEmail());
	}
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Key getWordKey() {
		return wordKey;
	}
	public void setWordKey(Key wordKey) {
		this.wordKey = wordKey;
	}
	public Key getUserKey() {
		return userKey;
	}
	public void setUserKey(Key userKey) {
		this.userKey = userKey;
	}
	public Key getPrimarykey() {
		return Primarykey;
	}
	public void setPrimarykey(Key primarykey) {
		Primarykey = primarykey;
	}
	public Integer getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
	}
	public Integer getWrongCount() {
		return wrongCount;
	}
	public void setWrongCount(Integer wrongCount) {
		this.wrongCount = wrongCount;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Float getAnswerRate() {
		return answerRate;
	}
	public void setAnswerRate(Float answerRate) {
		this.answerRate = answerRate;
	}
	public Float getWrongRate() {
		return wrongRate;
	}
	public void setWrongRate(Float wrongRate) {
		this.wrongRate = wrongRate;
	}
	public int getMAX_DELAY_COUNT() {
		return MAX_DELAY_COUNT;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getWordName() {
		return wordName;
	}
	public void setWordName(String wordName) {
		this.wordName = wordName;
	}
	public VBWord getWord() {
		return word;
	}
	public void setWord(VBWord word) {
		this.word = word;
	}
	public Integer getDelayCount() {
		return delayCount;
	}
	public void setDelayCount(Integer delayCount) {
		this.delayCount = delayCount;
	}
	public Integer getInsertCount() {
		return insertCount;
	}
	public void setInsertCount(Integer insertCount) {
		this.insertCount = insertCount;
	}

	@Override
	public String toString() {
		return "VBWordMap [answerCount=" + answerCount + ", answerRate="
				+ answerRate + ", delayCount=" + delayCount + ", insertCount="
				+ insertCount + ", score=" + score + ", totalCount="
				+ totalCount + ", wordName=" + wordName + ", wrongCount="
				+ wrongCount + ", wrongRate=" + wrongRate + "]";
	}
	
	
}
