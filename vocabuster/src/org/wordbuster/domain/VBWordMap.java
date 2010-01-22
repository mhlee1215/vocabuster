package org.wordbuster.domain;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class VBWordMap {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
//	@Persistent//(mappedBy = "wordMap")
//	private User user;
	
	@Persistent//(mappedBy = "wordMap")
	private Key userKey;
	
	@Persistent//(mappedBy = "wordMap")
	private Key wordKey;

	@Persistent
	private Integer score;
	
	@Persistent
	private Integer insertCount;
	
	public Integer getInsertCount() {
		return insertCount;
	}
	public void setInsertCount(Integer insertCount) {
		this.insertCount = insertCount;
	}
	public void wrong(){
		score-=5;
	}
	public void correct(){
		score+=1;
	}
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
//	public User getUser() {
//		return user;
//	}
//	public void setUser(User user) {
//		this.user = user;
//	}
	public Key getWordKey() {
		return wordKey;
	}
	public void setWordKey(Key wordKey) {
		this.wordKey = wordKey;
	}
	
	public void increaseInsertCount(){
		insertCount++;
	}
	public Key getUserKey() {
		return userKey;
	}
	public void setUserKey(Key userKey) {
		this.userKey = userKey;
	}

	
	
}
