package com.lawencon.community.service;

import org.springframework.stereotype.Service;

@Service
public class PaginationService {

    public int getPageCount(int totalCount, int pageSize) {
        int pageCount = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            pageCount++;
        }
        return pageCount;


    }
}
