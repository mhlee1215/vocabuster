package org.wordbuster.domain;

public class VBNavigationVO {
	private int pageIndex = 1;
	//조회 페이지 사이즈
	private final int pageSize = 15;
	//조회 오프셋
	private int pageOffset = 0;
	//페이징 블록의 사이즈(갯수)
	private int blockSize = 10;
	
	private String pageName;

	
	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
		pageOffset = (pageIndex-1) * pageSize;
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
