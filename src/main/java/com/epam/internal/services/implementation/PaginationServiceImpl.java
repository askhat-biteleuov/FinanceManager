package com.epam.internal.services.implementation;

import com.epam.internal.dtos.PaginationDto;

public class PaginationServiceImpl {
    public PaginationDto createPagination(long itemId, int selectedPage, int pageSize, long collectionSize, String url) {
        int firstItem = (selectedPage - 1) * pageSize;
        int pageCount = (int) Math.ceil(collectionSize / pageSize);
        int startPage = selectedPage - 5 > 0 ? selectedPage - 5 : 1;
        int endPage = startPage + 10;
        if (endPage > pageCount) {
            endPage = pageCount;
            if (endPage > 10) {
                startPage = startPage - (5 - (endPage - selectedPage));
            }
        }
        return new PaginationDto(itemId, firstItem, pageCount, startPage, endPage, selectedPage, url);
    }
}
