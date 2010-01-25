package org.wordbuster.domain;

public class VBWordSearchVO {
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
	 * 정렬순 
	 */
	private String searchOrder;
	/**
	 * 조회 결과 타입 (i.e. 테이블, 리스트..) 
	 */
	private String searchResultType;
	
	
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
	public String getSearchOrder() {
		return searchOrder;
	}
	public void setSearchOrder(String searchOrder) {
		this.searchOrder = searchOrder;
	}
	@Override
	public String toString() {
		return "VBWordSearchVO [searchCategory=" + searchCategory
				+ ", searchKeyword=" + searchKeyword + ", searchOrder="
				+ searchOrder + ", searchResultType=" + searchResultType
				+ ", searchType=" + searchType + "]";
	}
	
	
}
