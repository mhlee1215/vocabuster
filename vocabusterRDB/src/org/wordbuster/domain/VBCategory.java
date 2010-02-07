package org.wordbuster.domain;


public class VBCategory {
	private String name;
	
	private VBWord word;

    public VBWord getWord() {
		return word;
	}

	public void setWord(VBWord word) {
		this.word = word;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
