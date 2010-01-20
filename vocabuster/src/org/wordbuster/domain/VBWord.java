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
	
	@Persistent(mappedBy = "word")
	private List<VBCategory> CategoryList;

	@Persistent
	private Integer insertedCount = 0;
	
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
}
