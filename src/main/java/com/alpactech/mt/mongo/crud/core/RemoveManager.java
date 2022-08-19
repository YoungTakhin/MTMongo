package com.alpactech.mt.mongo.crud.core;

import org.springframework.data.mongodb.core.query.Query;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public interface RemoveManager<E> {
    void remove(Query query);

    void remove(E entity);

    void removeById(Serializable id);

    void removeById(E entity);

    void removeByField(String field, Object value);

    void removeByFields(Map<String, Object> fieldMap);

    void removeByIds(Collection<? extends Serializable> idList);
}
