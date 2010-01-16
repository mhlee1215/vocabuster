package org.wordbuster.domain;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class VBWord {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	public String wordName;
	
	@Persistent(mappedBy = "word")
	private List<VBWordInfo> wordInfoList;
	
	@NotPersistent//(mappedBy = "word")
	private List<VBWordCategory> wordCategory;

	@Persistent
	private int insertedCount;
	
	public int getInsertedCount() {
		return insertedCount;
	}

	public void setInsertedCount(int insertedCount) {
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

	public List<VBWordCategory> getWordCategory() {
		return wordCategory;
	}

	public void setWordCategory(List<VBWordCategory> wordCategory) {
		this.wordCategory = wordCategory;
	}
	
	
	
}
