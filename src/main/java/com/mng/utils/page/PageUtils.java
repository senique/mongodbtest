package com.mng.utils.page;

public class PageUtils {

	public static int getNowPage(Integer page, int totalPageNum) {
		int nowPage = page != null && page > 0 ? page : 1;
		if (page != null && page > totalPageNum) {
			return totalPageNum;
		}
		return nowPage;
	}

	public static int getTotalPageNum(int pageSize, int totalCount) {

		if (totalCount != 0) {
			return totalCount % pageSize == 0 ? totalCount / pageSize
					: totalCount / pageSize + 1;
		}
		// count为0计为有一页
		return 1;
	}

	public static int getPageSize(Integer size) {
		return size != null && size > 0 ? size : 10;
	}

}
