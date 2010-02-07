package org.wordbuster.domain;

public class VBWordInfo {
	private VBWord word;
	
	//사전용 전체 의미
	private String meaning;
	
	//테스트용 짧은 의미
	private String shortMeaning;
	
	private VBWordCategory vBWordCategory;
	
	public String getShortMeaning() {
		return shortMeaning;
	}
	public void setShortMeaning(String shortMeaning) {
		this.shortMeaning = shortMeaning;
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
