package com.alpactech.mt.mongo.crud.impl;

import com.alpactech.mt.mongo.Manager;
import com.alpactech.mt.mongo.base.AbstractMongoTemplateGetter;
import com.alpactech.mt.mongo.crud.ViewManager;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Manager
public abstract class ViewManagerImpl<E> extends AbstractMongoTemplateGetter<E> implements ViewManager<E> {
    /**
     * @param query
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<E> page(Query query, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return new PageImpl<>(this.getMongoTemplate().find(query.with(pageable), this.getEntityClass()),
                pageable, this.getMongoTemplate().count(query, this.getEntityClass()));
    }

    /**
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<E> page(Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return new PageImpl<>(this.getMongoTemplate().find(new Query().with(pageable), this.getEntityClass()),
                pageable, this.getMongoTemplate().count(new Query(), this.getEntityClass()));
    }

    /**
     * @param pageNum
     * @param pageSize
     * @param sortBy
     * @return
     */
    @Override
    public Page<E> page(Integer pageNum, Integer pageSize, String... sortBy) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(sortBy));
        return new PageImpl<>(this.getMongoTemplate().find(new Query().with(pageable), this.getEntityClass()),
                pageable, this.getMongoTemplate().count(new Query(), this.getEntityClass()));
    }

    /**
     * @param query
     * @param pageNum
     * @param pageSize
     * @param sortBy
     * @return
     */
    @Override
    public Page<E> page(Query query, Integer pageNum, Integer pageSize, String... sortBy) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(sortBy));
        return new PageImpl<>(this.getMongoTemplate().find(query.with(pageable), this.getEntityClass()),
                pageable, this.getMongoTemplate().count(query, this.getEntityClass()));
    }

    /**
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @param sortBy
     * @return
     */
    @Override
    public Page<E> page(Integer pageNum, Integer pageSize, Sort.Direction orderBy, String... sortBy) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(orderBy, sortBy));
        return new PageImpl<>(this.getMongoTemplate().find(new Query().with(pageable), this.getEntityClass()),
                pageable, this.getMongoTemplate().count(new Query(), this.getEntityClass()));
    }

    /**
     * @param query
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @param sortBy
     * @return
     */
    @Override
    public Page<E> page(Query query, Integer pageNum, Integer pageSize, Sort.Direction orderBy, String... sortBy) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(orderBy, sortBy));
        return new PageImpl<>(this.getMongoTemplate().find(new Query().with(pageable), this.getEntityClass()),
                pageable, this.getMongoTemplate().count(new Query(), this.getEntityClass()));
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<E> page(Pageable pageable) {
        return new PageImpl<>(this.getMongoTemplate().find(new Query().with(pageable), this.getEntityClass()), pageable,
                this.getMongoTemplate().count(new Query(), this.getEntityClass()));
    }

    /**
     * @param id
     * @return
     */
    @Override
    public E getById(Serializable id) {
        return this.getMongoTemplate().findById(id, this.getEntityClass());
    }

    /**
     * @param field
     * @param value
     * @return
     */
    @Override
    public E getByField(String field, Object value) {
        return this.getMongoTemplate().findOne(new Query(Criteria.where(field).is(value)), this.getEntityClass());
    }

    /**
     * @param fieldMap
     * @return
     */
    @Override
    public E getByFields(Map<String, Object> fieldMap) {
        Query query = new Query();
        fieldMap.forEach((field, value) -> query.addCriteria(Criteria.where(field).is(value)));
        return this.getMongoTemplate().findOne(query, this.getEntityClass());
    }

    /**
     * @param query
     * @return
     */
    @Override
    public E getOne(Query query) {
        return this.getMongoTemplate().findOne(query, this.getEntityClass());
    }

    /**
     * @param query
     * @return
     */
    @Override
    public List<E> list(Query query) {
        return this.getMongoTemplate().find(query, this.getEntityClass());
    }

    /**
     * @return
     */
    @Override
    public List<E> list() {
        return this.getMongoTemplate().findAll(this.getEntityClass());
    }

    /**
     * @param idList
     * @return
     */
    @Override
    public List<E> listByIds(Collection<? extends Serializable> idList) {
        return this.getMongoTemplate().find(Query.query(Criteria.where("_id").in(idList)), this.getEntityClass());
    }

    /**
     * @param columnMap
     * @return
     */
    @Override
    public List<E> listByFields(Map<String, Object> columnMap) {
        Query query = new Query();
        columnMap.forEach((field, value) -> query.addCriteria(Criteria.where(field).is(value)));
        return this.getMongoTemplate().find(query, this.getEntityClass());
    }
}
