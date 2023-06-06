package com.alpactech.mt.mongo.crud.geo;

import org.springframework.data.domain.Page;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collection;
import java.util.Map;

public interface GeoPageManager<E> {
    /*
    根据圆形查询列表
     */
    Page<E> pageByCircle(String field, Circle circle);

    Page<E> pageByCircle(Collection<String> fields, Circle circle);

    Page<E> pageByCircle(String field, Point center, Distance radius);

    Page<E> pageByCircle(Collection<String> fields, Point center, Distance radius);

    Page<E> pageByCircle(Map<String, Circle> fieldMap);

    Page<E> pageByCircle(Query query, String field, Circle circle);

    Page<E> pageByCircle(Query query, Collection<String> fields, Circle circle);

    Page<E> pageByCircle(Query query, String field, Point center, Distance radius);

    Page<E> pageByCircle(Query query, Collection<String> fields, Point center, Distance radius);

    Page<E> pageByCircle(Query query, Map<String, Circle> fieldMap);

    /*
    根据矩形查询列表
     */
    Page<E> pageByBox(String field, Box box);

    Page<E> pageByBox(Collection<String> fields, Box box);

    Page<E> pageByBox(String field, Point first, Point second);

    Page<E> pageByBox(Collection<String> fields, Point first, Point second);

    Page<E> pageByBox(Map<String, Box> fieldMap);

    Page<E> pageByBox(Query query, String field, Box box);

    Page<E> pageByBox(Query query, Collection<String> fields, Box box);

    Page<E> pageByBox(Query query, String field, Point first, Point second);

    Page<E> pageByBox(Query query, Collection<String> fields, Point first, Point second);

    Page<E> pageByBox(Query query, Map<String, Box> fieldMap);

    /*
    根据多边形查询列表
     */
    Page<E> pageByPolygon(String field, Polygon polygon);

    Page<E> pageByPolygon(Collection<String> fields, Polygon polygon);

    Page<E> pageByPolygon(Map<String, Polygon> fieldMap);

    Page<E> pageByPolygon(Query query, String field, Polygon polygon);

    Page<E> pageByPolygon(Query query, Collection<String> fields, Polygon polygon);

    Page<E> pageByPolygon(Query query, Map<String, Polygon> fieldMap);
}
