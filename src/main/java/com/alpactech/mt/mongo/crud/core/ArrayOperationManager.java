package com.alpactech.mt.mongo.crud.core;

import org.springframework.data.mongodb.core.query.Query;

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

    E pull(Query query, String field, Object element);

    E pull(Serializable id, String field, Object element);

    E pull(E entity, String field, Object element);

    E pullAll(Query query, String field, Object... elements);

    E pullAll(Serializable id, String field, Object... elements);

    E pullAll(E entity, String field, Object... elements);

    E pullAll(Query query, String field, Collection<Object> elements);

    E pullAll(Serializable id, String field, Collection<Object> elements);

    E pullAll(E entity, String field, Collection<Object> elements);

    E distinct(Query query, String field);

    E distinct(Serializable id, String field);

    E distinct(E entity, String field);
}
