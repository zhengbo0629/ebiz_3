package com.ebiz.model;

public class PageSplitHelper {
    private static final Integer DEFAULT_PAGE_SIZE = 5;
    /**
     * 开始行数
     */
    protected int startRow = 0;
    /**
     * 每页记录数
     */
    protected int pageSize = 5;
    /**
     * 当前在第几页 从1开始
     */
    private int pageIndex;
    /**
     * 总记录数
     */
    private long totalCount;
    /**
     * 总页面数
     */
    private int totalPages;

    public PageSplitHelper() {
    }

    public PageSplitHelper(int pageIndex, int pageSize) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        if (pageSize <= 0) {
            pageSize = 5;
        }
        this.pageIndex = pageIndex;
        this.startRow = (pageIndex - 1) * pageSize;
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
        this.startRow = (pageIndex - 1) * pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(long totalCount) {
        if (totalCount < 0L) {
            this.totalCount = 0L;
            totalPages = 0;
        } else {
            this.totalCount = totalCount;
            totalPages = (int) Math.ceil((double) this.totalCount / (double) this.pageSize);
        }

    }

    public int getTotalPages() {
        if (totalCount <= 0L) {
            totalPages = 0;
        } else {
            totalPages = (int) Math.ceil((double) this.totalCount / (double) this.pageSize);
        }
        return totalPages;
    }
}
