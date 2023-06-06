package com.alpactech.mt.mongo.crud.core;

import com.alpactech.mt.mongo.crud.geo.GeoPageManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collection;
import java.util.Map;

public interface PageManager<E> extends GeoPageManager<E> {
    /*
    分页
     */
    Page<E> page(Query query, Integer pageNum, Integer pageSize);

    Page<E> page(Query query, Integer pageNum, Integer pageSize, String... sortBy);

    Page<E> page(Query query, Integer pageNum, Integer pageSize, Sort.Direction orderBy, String... sortBy);

    Page<E> pageAll(Integer pageNum, Integer pageSize);

    Page<E> pageAll(Integer pageNum, Integer pageSize, String... sortBy);

    Page<E> pageAll(Integer pageNum, Integer pageSize, Sort.Direction orderBy, String... sortBy);

    Page<E> pageAll(Pageable pageable);

    Page<E> pageByField(String field, Object value, Integer pageNum, Integer pageSize);

    Page<E> pageByField(String field, Object value, Integer pageNum, Integer pageSize, String... sortBy);

    Page<E> pageByField(String field, Object value, Integer pageNum, Integer pageSize, Sort.Direction orderBy, String... sortBy);

    Page<E> pageByFields(Map<String, Object> fieldMap, Integer pageNum, Integer pageSize);

    Page<E> pageByFields(Map<String, Object> fieldMap, Integer pageNum, Integer pageSize, String... sortBy);

    Page<E> pageByFields(Map<String, Object> fieldMap, Integer pageNum, Integer pageSize, Sort.Direction orderBy, String... sortBy);

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
