package com.alpactech.mt.mongo.crud.core;

import org.springframework.data.mongodb.core.query.Query;

import java.io.Serializable;
import java.util.Map;

public interface JudgeAndCountManager<E> {
    Long count();

    Long count(Query query);

    Long countByField(String field, Object value);

    Long countByFields(Map<String, Object> fieldMap);

    Boolean exists(Query query);

    Boolean existsById(Serializable id);

    Boolean existsByField(String field, Object value);

    Boolean existsByFields(Map<String, Object> fieldMap);
}
