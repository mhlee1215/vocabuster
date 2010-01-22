package org.wordbuster.domain;

public class VBWordSearchVO {
	private String searchType;
	private String searchCategory;
	private String searchKeyword;
	
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchCategory() {
		return searchCategory;
	}
	public void setSearchCategory(String searchCategory) {
		this.searchCategory = searchCategory;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	@Override
	public String toString() {
		return "VBWordSearchVO [searchCategory=" + searchCategory
				+ ", searchKeyword=" + searchKeyword + ", searchType="
				+ searchType + "]";
	}
}
