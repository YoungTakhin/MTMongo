package com.alpactech.mt.mongo.crud.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;

public interface PageManager<E> {
    /*
    分页
     */
    Page<E> page(Query query, Integer pageNum, Integer pageSize);

    Page<E> page(Integer pageNum, Integer pageSize);

    Page<E> page(Integer pageNum, Integer pageSize, String... sortBy);

    Page<E> page(Query query, Integer pageNum, Integer pageSize, String... sortBy);

    Page<E> page(Integer pageNum, Integer pageSize, Sort.Direction orderBy, String... sortBy);

    Page<E> page(Query query, Integer pageNum, Integer pageSize, Sort.Direction orderBy, String... sortBy);

    Page<E> page(Pageable pageable);

    /*
    去重
     */
//    Page<E> page(Query query, Integer pageNum, Integer pageSize);
//
//    Page<E> page(Integer pageNum, Integer pageSize);
//
//    Page<E> page(Integer pageNum, Integer pageSize, String... sortBy);
//
//    Page<E> page(Query query, Integer pageNum, Integer pageSize, String... sortBy);
//
//    Page<E> page(Integer pageNum, Integer pageSize, Sort.Direction orderBy, String... sortBy);
//
//    Page<E> page(Query query, Integer pageNum, Integer pageSize, Sort.Direction orderBy, String... sortBy);
//
//    Page<E> page(Pageable pageable);
}
