package org.wordbuster.domain;

public class VBNavigationVO {
	private int pageIndex = 0;
	//조회 페이지 사이즈
	private final int pageSize = 30;
	//조회 오프셋
	private int pageOffset = 0;
	
	private String pageName;

	
	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
		pageOffset = pageIndex * pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageOffset() {
		return pageOffset;
	}

	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
}
