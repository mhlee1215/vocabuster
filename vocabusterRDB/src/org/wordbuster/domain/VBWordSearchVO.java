package org.wordbuster.domain;

public class VBWordSearchVO extends VBNavigationVO{
	/**
	 * 조회 타입 (i.e. 의미, 단어명
	 */
	private String searchType;
	/**
	 * 조회 카테고리
	 */
	private String searchCategory;
	/**
	 * 조회 키워드
	 */
	private String searchKeyword;
	/**
	 * 정렬 스트링
	 */
	private String searchOrderString;
	/**
	 * 조회 결과 타입 (i.e. 테이블, 리스트..) 
	 */
	private String searchResultType;

	public String getSearchOrderString() {
		return searchOrderString;
	}
	public void setSearchOrderString(String searchOrderString) {
		this.searchOrderString = searchOrderString;
	}
	public String getSearchResultType() {
		return searchResultType;
	}
	public void setSearchResultType(String searchResultType) {
		this.searchResultType = searchResultType;
	}
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
				+ ", searchKeyword=" + searchKeyword + ", searchOrderString="
				+ searchOrderString + ", searchResultType=" + searchResultType
				+ ", searchType=" + searchType + "]";
	}
	
	
}
