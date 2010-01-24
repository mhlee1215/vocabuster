package org.wordbuster.domain;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class VBUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5901428787783894579L;

	/**
	 * 프라이머리 키
	 */
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
    /**
     * 구글 계정 사용자
     */
    @Persistent
	private User user;
    
    /**
     * 최초 사용일
     */
    @Persistent
    private Date firstUseDate;
    
    /**
     * 마지막 사용일
     */
    @Persistent
    private Date latestUseDate;
    
    /**
     * 보유한 단어
     */
    @Persistent
    private Set<Key> wordMapKey;
    
    @Persistent
    private List<VBWordMap> wordMapList;

    
    public List<VBWordMap> getWordMapList() {
		return wordMapList;
	}

	public void setWordMapList(List<VBWordMap> wordMapList) {
		this.wordMapList = wordMapList;
	}

	public Set<Key> getWordMapKey() {
		return wordMapKey;
	}

	public void setWordMapKey(Set<Key> wordMapKey) {
		this.wordMapKey = wordMapKey;
	}

	public VBUser(){
    	
    }
    
    public VBUser(String nickName){
    	
    }
    
    public VBUser(Key key, User user, Date firstUseDate, Date latestUseDate){
    	this.key = key;
    	this.user = user;
    	this.firstUseDate = firstUseDate;
    	this.latestUseDate = latestUseDate;
    }
    
//	public List<VBWordMap> getWordMap() {
//		return wordMap;
//	}
//
//
//	public void setWordMap(List<VBWordMap> wordMap) {
//		this.wordMap = wordMap;
//	}


	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getFirstUseDate() {
		return firstUseDate;
	}

	public void setFirstUseDate(Date firstUseDate) {
		this.firstUseDate = firstUseDate;
	}

	public Date getLatestUseDate() {
		return latestUseDate;
	}

	public void setLatestUseDate(Date latestUseDate) {
		this.latestUseDate = latestUseDate;
	}
    
    

}
