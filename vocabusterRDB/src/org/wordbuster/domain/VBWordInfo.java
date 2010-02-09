package org.wordbuster.domain;

public class VBWordInfo {

	//단어명
	private String wordname;
	//단어의 뜻 순번
	private String seq;
	//사전용 전체 의미
	private String fullmeaning;
	//테스트용 짧은 의미
	private String shortmeaning;
	//카테고리 아이디 
	private String categoryid;
	
	public String getShortmeaning() {
		return shortmeaning;
	}
	public void setShortmeaning(String shortmeaning) {
		this.shortmeaning = shortmeaning;
	}
	
	public String getWordname() {
		return wordname;
	}
	public void setWordname(String wordname) {
		this.wordname = wordname;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getFullmeaning() {
		return fullmeaning;
	}
	public void setFullmeaning(String fullmeaning) {
		this.fullmeaning = fullmeaning;
	}
	public String getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
	
	@Override
	public String toString() {
		return "VBWordInfo [categoryid=" + categoryid + ", fullmeaning="
				+ fullmeaning + ", seq=" + seq + ", shortmeaning="
				+ shortmeaning + ", wordname=" + wordname + "]";
	}
    
	
    
}
