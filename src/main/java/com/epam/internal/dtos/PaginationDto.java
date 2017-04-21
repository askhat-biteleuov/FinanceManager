package com.epam.internal.dtos;

public class PaginationDto {
    private long itemId;
    private int firstItem;
    private int pageCount;
    private int startPage;
    private int endPage;
    private int selectedPage;
    private String url;

    public PaginationDto() {
    }

    public PaginationDto(long itemId, int firstItem, int pageCount, int startPage, int endPage, int selectedPage, String url) {
        this.itemId = itemId;
        this.firstItem = firstItem;
        this.pageCount = pageCount;
        this.startPage = startPage;
        this.endPage = endPage;
        this.selectedPage = selectedPage;
        this.url = url;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public int getFirstItem() {
        return firstItem;
    }

    public void setFirstItem(int firstItem) {
        this.firstItem = firstItem;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getSelectedPage() {
        return selectedPage;
    }

    public void setSelectedPage(int selectedPage) {
        this.selectedPage = selectedPage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
