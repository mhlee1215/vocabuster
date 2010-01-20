package org.wordbuster.domain;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class VBWordInfo {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private VBWord word;
	
	//사전용 전체 의미
	@Persistent
	private String meaning;
	
	//테스트용 짧은 의미
	@Persistent
	private String shortMeaning;
	
	@Persistent
	private VBWordCategory vBWordCategory;
	
	public String getShortMeaning() {
		return shortMeaning;
	}
	public void setShortMeaning(String shortMeaning) {
		this.shortMeaning = shortMeaning;
	}
	
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public VBWord getWord() {
		return word;
	}
	public void setWord(VBWord word) {
		this.word = word;
	}
	public String getMeaning() {
		return meaning;
	}
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
    public VBWordCategory getvBWordCategory() {
        return vBWordCategory;
    }
    public void setvBWordCategory(VBWordCategory vBWordCategory) {
        this.vBWordCategory = vBWordCategory;
    }
}
