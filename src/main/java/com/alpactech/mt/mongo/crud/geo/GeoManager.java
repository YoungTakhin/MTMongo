package com.alpactech.mt.mongo.crud.geo;

import com.alpactech.mt.mongo.crud.CrudManager;
import org.springframework.data.domain.Page;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface GeoManager<E> {
    /*
    根据圆形查询列表
     */
    List<E> listByCircle(String field, Circle circle);

    List<E> listByCircle(Collection<String> fields, Circle circle);

    List<E> listByCircle(String field, Point center, Distance radius);

    List<E> listByCircle(Collection<String> fields, Point center, Distance radius);

    List<E> listByCircle(Map<String, Circle> fieldMap);

    List<E> listByCircle(Query query, String field, Circle circle);

    List<E> listByCircle(Query query, Collection<String> fields, Circle circle);

    List<E> listByCircle(Query query, String field, Point center, Distance radius);

    List<E> listByCircle(Query query, Collection<String> fields, Point center, Distance radius);

    List<E> listByCircle(Query query, Map<String, Circle> fieldMap);

    /*
    根据矩形查询列表
     */
    List<E> listByBox(String field, Box box);

    List<E> listByBox(Collection<String> fields, Box box);

    List<E> listByBox(String field, Point first, Point second);

    List<E> listByBox(Collection<String> fields, Point first, Point second);

    List<E> listByBox(Map<String, Box> fieldMap);

    List<E> listByBox(Query query, String field, Box box);

    List<E> listByBox(Query query, Collection<String> fields, Box box);

    List<E> listByBox(Query query, String field, Point first, Point second);

    List<E> listByBox(Query query, Collection<String> fields, Point first, Point second);

    List<E> listByBox(Query query, Map<String, Box> fieldMap);

    /*
    根据多边形查询列表
     */
    List<E> listByPolygon(String field, Polygon polygon);

    List<E> listByPolygon(Collection<String> fields, Polygon polygon);

    List<E> listByPolygon(Map<String, Polygon> fieldMap);

    List<E> listByPolygon(Query query, String field, Polygon polygon);

    List<E> listByPolygon(Query query, Collection<String> fields, Polygon polygon);

    List<E> listByPolygon(Query query, Map<String, Polygon> fieldMap);
}
