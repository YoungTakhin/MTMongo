package com.alpactech.mt.mongo.crud.core;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface UpdateManager<E> extends ArrayOperationManager<E> {
    FindAndModifyOptions FIND_AND_MODIFY_OPTIONS = FindAndModifyOptions.options().returnNew(true);

    E update(E entity, Update update);

    E update(Query query, E entity);

    E update(Query query, Update update);

    E update(E sourceEntity, E updateEntity);

    E updateById(Serializable id, Update update);

    E updateById(Serializable id, E entity);

    E updateById(Serializable id, Map<String, Object> fieldMap);

    E updateById(E entity);

    E updateByFields(Map<String, Object> fieldMap, Update update);

    E updateByFields(Map<String, Object> fieldMap, E entity);

    List<E> updateBatchByIds(Collection<E> entityList);

    List<E> updateBatchByIds(Map<Serializable, Update> updateMap);

    List<E> updateBatchByIds(Collection<Serializable> ids, Update update);

    E updateFields(E entity, Map<String, Object> fieldMap);

    E updateFieldById(Serializable id, String field, Object value);

    E updateFieldsById(Serializable id, Map<String, Object> fieldMap);

    E updateFieldByField(String field, Object value, String updateField, Object updateValue);

    E updateFieldByFields(Map<String, Object> fieldMap, String updateField, Object updateValue);

    E updateFieldsByField(String field, Object value, Map<String, Object> updateFieldMap);

    E updateFieldsByFields(Map<String, Object> fieldMap, Map<String, Object> updateFieldMap);
}
