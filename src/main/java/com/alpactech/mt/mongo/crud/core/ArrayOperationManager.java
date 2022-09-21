package com.alpactech.mt.mongo.crud.core;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.Serializable;
import java.util.Collection;

public interface ArrayOperationManager<E> {
    E addToSet(Query query, String field, Object element);

    E addToSet(Serializable id, String field, Object element);

    E addToSet(E entity, String field, Object element);

    E addAllToSet(Query query, String field, Object... elements);

    E addAllToSet(Serializable id, String field, Object... elements);

    E addAllToSet(E entity, String field, Object... elements);

    E addAllToSet(Query query, String field, Collection<Object> elements);

    E addAllToSet(Serializable id, String field, Collection<Object> elements);

    E addAllToSet(E entity, String field, Collection<Object> elements);

    E push(Query query, String field, Object element);

    E push(Serializable id, String field, Object element);

    E push(E entity, String field, Object element);

    E pushAll(Query query, String field, Object... element);

    E pushAll(Serializable id, String field, Object... element);

    E pushAll(E entity, String field, Object... element);

    E pushAll(Query query, String field, Collection<Object> elements);

    E pushAll(Serializable id, String field, Collection<Object> elements);

    E pushAll(E entity, String field, Collection<Object> elements);

    E pull(Query query, String field, Object element);

    E pull(Serializable id, String field, Object element);

    E pull(E entity, String field, Object element);

    E pullAll(Query query, String field, Object... elements);

    E pullAll(Serializable id, String field, Object... elements);

    E pullAll(E entity, String field, Object... elements);

    E pullAll(Query query, String field, Collection<Object> elements);

    E pullAll(Serializable id, String field, Collection<Object> elements);

    E pullAll(E entity, String field, Collection<Object> elements);

    E pop(Query query, String field, Update.Position position);

    E pop(Serializable id, String field, Update.Position position);

    E pop(E entity, String field, Update.Position position);

    E distinct(Query query, String field);

    E distinct(Serializable id, String field);

    E distinct(E entity, String field);
}
