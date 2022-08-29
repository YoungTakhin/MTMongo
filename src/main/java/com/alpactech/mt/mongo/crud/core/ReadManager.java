package com.alpactech.mt.mongo.crud.core;

import org.springframework.data.mongodb.core.query.Query;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ReadManager<E> extends PageManager<E>, JudgeAndCountManager<E> {

    /*
    单查
     */
    E getById(Serializable id);

    E getByField(String field, Object value);

    E getByFields(Map<String, Object> fieldMap);

    E getOne(Query query);

    /*
    多查
     */
    List<E> list();

    List<E> list(Query query);

    List<E> listByIds(Collection<? extends Serializable> idList);

    List<E> listByFields(Map<String, Object> fieldMap);
}
