package org.wordbuster.domain;

public class VBMyWordSearchVO extends VBNavigationVO{
	private String searchUserid;
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
	
	/**
	 * 뜻을 찾았는지 여부
	 */
	private String searchIsvalid;

	/**
	 *  조회 제외 케워드
	 */
	private String searchExcludeKeyword;
	
	public String getSearchExcludeKeyword() {
		return searchExcludeKeyword;
	}
	public void setSearchExcludeKeyword(String searchExcludeKeyword) {
		this.searchExcludeKeyword = searchExcludeKeyword;
	}
	public String getSearchIsvalid() {
		return searchIsvalid;
	}
	public void setSearchIsvalid(String searchIsvalid) {
		this.searchIsvalid = searchIsvalid;
	}
	
	public String getSearchUserid() {
		return searchUserid;
	}
	public void setSearchUserid(String searchUserid) {
		this.searchUserid = searchUserid;
	}
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
		return "VBMyWordSearchVO [searchCategory=" + searchCategory
				+ ", searchKeyword=" + searchKeyword + ", searchOrderString="
				+ searchOrderString + ", searchResultType=" + searchResultType
				+ ", searchType=" + searchType + ", searchUserid="
				+ searchUserid + "]";
	}
	
	
	
	
}
