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

/**
 * 视图集合管理器实现类
 *
 * <p>用于操作视图集合，该类中的方法均为只读操作
 *
 * @param <E>
 * @since 1.1.0
 */
@Manager
public abstract class ViewManagerImpl<E> extends AbstractMongoTemplateGetter<E> implements ViewManager<E> {
    /**
     * 分页查询
     * 
     * @param query 查询对象
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return 分页对象
     */
    @Override
    public Page<E> page(Query query, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return new PageImpl<>(this.getMongoTemplate().find(query.with(pageable), this.getEntityClass()),
                pageable, this.getMongoTemplate().count(query, this.getEntityClass()));
    }

    /**
     * 分页查询
     * 
     * @param query 查询对象
     * @param pageNum 页码
     * @param pageSize 页大小
     * @param sortBy 排序字段
     * @return 分页对象
     */
    @Override
    public Page<E> page(Query query, Integer pageNum, Integer pageSize, String... sortBy) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(sortBy));
        return new PageImpl<>(this.getMongoTemplate().find(query.with(pageable), this.getEntityClass()),
                pageable, this.getMongoTemplate().count(query, this.getEntityClass()));
    }

    /**
     * 分页查询
     * 
     * @param pageNum 页码
     * @param pageSize 页大小
     * @param orderBy 排序方式
     * @param sortBy 排序字段
     * @return 分页对象
     */
    @Override
    public Page<E> pageAll(Integer pageNum, Integer pageSize, Sort.Direction orderBy, String... sortBy) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(orderBy, sortBy));
        return new PageImpl<>(this.getMongoTemplate().find(new Query().with(pageable), this.getEntityClass()),
                pageable, this.getMongoTemplate().count(new Query(), this.getEntityClass()));
    }

    /**
     * 分页查询
     * 
     * @param query 查询对象
     * @param pageNum 页码
     * @param pageSize 页大小
     * @param orderBy 排序方式
     * @param sortBy 排序字段
     * @return 分页对象
     */
    @Override
    public Page<E> page(Query query, Integer pageNum, Integer pageSize, Sort.Direction orderBy, String... sortBy) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(orderBy, sortBy));
        return new PageImpl<>(this.getMongoTemplate().find(query.with(pageable), this.getEntityClass()),
                pageable, this.getMongoTemplate().count(query, this.getEntityClass()));
    }

    /**
     * 分页查询所有文档
     * 
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return 分页对象
     */
    @Override
    public Page<E> pageAll(Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return new PageImpl<>(this.getMongoTemplate().find(new Query().with(pageable), this.getEntityClass()),
                pageable, this.getMongoTemplate().count(new Query(), this.getEntityClass()));
    }

    /**
     * 分页查询所有文档
     *
     * @param pageNum 页码
     * @param pageSize 页大小
     * @param sortBy 排序字段
     * @return 分页对象
     */
    @Override
    public Page<E> pageAll(Integer pageNum, Integer pageSize, String... sortBy) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(sortBy));
        return new PageImpl<>(this.getMongoTemplate().find(new Query().with(pageable), this.getEntityClass()),
                pageable, this.getMongoTemplate().count(new Query(), this.getEntityClass()));
    }

    /**
     * 分页查询所有文档
     *
     * @param pageable 分页信息
     * @return 分页对象
     */
    @Override
    public Page<E> pageAll(Pageable pageable) {
        return new PageImpl<>(this.getMongoTemplate().find(new Query().with(pageable), this.getEntityClass()), pageable,
                this.getMongoTemplate().count(new Query(), this.getEntityClass()));
    }

    /**
     * 根据指定字段分页查询
     *
     * @param field 字段名
     * @param value 字段值
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return 分页对象
     */
    @Override
    public Page<E> pageByField(String field, Object value, Integer pageNum, Integer pageSize) {
        Query query = Query.query(Criteria.where(field).is(value));
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return new PageImpl<>(this.getMongoTemplate().find(query, this.getEntityClass()), pageable,
                this.getMongoTemplate().count(query, this.getEntityClass()));
    }

    /**
     * 根据指定字段分页查询
     *
     * @param field 字段名
     * @param value 字段值
     * @param pageNum 页码
     * @param pageSize 页大小
     * @param sortBy 排序字段
     * @return 分页对象
     */
    @Override
    public Page<E> pageByField(String field, Object value, Integer pageNum, Integer pageSize, String... sortBy) {
        Query query = Query.query(Criteria.where(field).is(value));
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(sortBy));
        return new PageImpl<>(this.getMongoTemplate().find(query, this.getEntityClass()), pageable,
                this.getMongoTemplate().count(query, this.getEntityClass()));
    }

    /**
     * 根据指定字段分页查询
     *
     * @param field 字段名
     * @param value 字段值
     * @param pageNum 页码
     * @param pageSize 页大小
     * @param orderBy 排序方式
     * @param sortBy 排序字段
     * @return 分页对象
     */
    @Override
    public Page<E> pageByField(String field, Object value, Integer pageNum, Integer pageSize, Sort.Direction orderBy, String... sortBy) {
        Query query = Query.query(Criteria.where(field).is(value));
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(orderBy, sortBy));
        return new PageImpl<>(this.getMongoTemplate().find(query, this.getEntityClass()), pageable,
                this.getMongoTemplate().count(query, this.getEntityClass()));
    }

    /**
     * 根据字段映射分页查询
     *
     * @param fieldMap 字段映射
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return 分页对象
     */
    @Override
    public Page<E> pageByFields(Map<String, Object> fieldMap, Integer pageNum, Integer pageSize) {
        Query query = new Query();
        fieldMap.forEach((field, value) ->  query.addCriteria(Criteria.where(field).is(value)));
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return new PageImpl<>(this.getMongoTemplate().find(query, this.getEntityClass()), pageable,
                this.getMongoTemplate().count(query, this.getEntityClass()));
    }

    /**
     * 根据字段映射分页查询
     *
     * @param fieldMap 字段映射
     * @param pageNum 页码
     * @param pageSize 页大小
     * @param sortBy 排序字段
     * @return 分页对象
     */
    @Override
    public Page<E> pageByFields(Map<String, Object> fieldMap, Integer pageNum, Integer pageSize, String... sortBy) {
        Query query = new Query();
        fieldMap.forEach((field, value) ->  query.addCriteria(Criteria.where(field).is(value)));
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(sortBy));
        return new PageImpl<>(this.getMongoTemplate().find(query, this.getEntityClass()), pageable,
                this.getMongoTemplate().count(query, this.getEntityClass()));
    }

    /**
     * 根据字段映射分页查询
     *
     * @param fieldMap 字段映射
     * @param pageNum 页码
     * @param pageSize 页大小
     * @param orderBy 排序方式
     * @param sortBy 排序字段
     * @return 分页对象
     */
    @Override
    public Page<E> pageByFields(Map<String, Object> fieldMap, Integer pageNum, Integer pageSize, Sort.Direction orderBy, String... sortBy) {
        Query query = new Query();
        fieldMap.forEach((field, value) ->  query.addCriteria(Criteria.where(field).is(value)));
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(orderBy, sortBy));
        return new PageImpl<>(this.getMongoTemplate().find(query, this.getEntityClass()), pageable,
                this.getMongoTemplate().count(query, this.getEntityClass()));
    }

    /**
     * 根据实体id查询实体文档
     *
     * @param id 实体id
     * @return 实体文档
     */
    @Override
    public E getById(Serializable id) {
        return this.getMongoTemplate().findById(id, this.getEntityClass());
    }

    /**
     * 根据指定字段查询实体文档
     *
     * @param field 字段名
     * @param value 字段值
     * @return 实体文档
     */
    @Override
    public E getByField(String field, Object value) {
        return this.getMongoTemplate().findOne(new Query(Criteria.where(field).is(value)), this.getEntityClass());
    }

    /**
     * 根据指定字段映射查询实体文档
     *
     * @param fieldMap 字段映射
     * @return 实体文档
     */
    @Override
    public E getByFields(Map<String, Object> fieldMap) {
        Query query = new Query();
        fieldMap.forEach((field, value) -> query.addCriteria(Criteria.where(field).is(value)));
        return this.getMongoTemplate().findOne(query, this.getEntityClass());
    }

    /**
     * 查询指定实体文档
     *
     * @param query 查询对象
     * @return 实体文档
     */
    @Override
    public E getOne(Query query) {
        return this.getMongoTemplate().findOne(query, this.getEntityClass());
    }

    /**
     * 查询指定文档列表
     *
     * @param query 查询对象
     * @return 实体文档列表
     */
    @Override
    public List<E> list(Query query) {
        return this.getMongoTemplate().find(query, this.getEntityClass());
    }

    /**
     * 查询所有文档
     *
     * @return 实体文档列表
     */
    @Override
    public List<E> list() {
        return this.getMongoTemplate().findAll(this.getEntityClass());
    }

    /**
     * 根据指定实体id列表查询实体文档列表
     *
     * @param idList 实体id列表
     * @return 实体文档列表
     */
    @Override
    public List<E> listByIds(Collection<? extends Serializable> idList) {
        return this.getMongoTemplate().find(Query.query(Criteria.where("_id").in(idList)), this.getEntityClass());
    }

    /**
     * 根据指定实体属性查询实体文档列表
     *
     * @param field 实体属性
     * @param value 实体属性值
     * @return 实体文档列表
     */
    @Override
    public List<E> listByField(String field, Object value) {
        return this.getMongoTemplate().find(Query.query(Criteria.where(field).is(value)), this.getEntityClass());
    }

    /**
     * 根据指定实体属性映射查询实体文档列表
     *
     * @param fieldMap 实体属性映射
     * @return 实体文档列表
     */
    @Override
    public List<E> listByFields(Map<String, Object> fieldMap) {
        Query query = new Query();
        fieldMap.forEach((field, value) -> query.addCriteria(Criteria.where(field).is(value)));
        return this.getMongoTemplate().find(query, this.getEntityClass());
    }

    /**
     * 计数
     *
     * @return 文档数
     */
    @Override
    public Long count() {
        return this.getMongoTemplate().count(new Query(), this.getEntityClass());
    }

    /**
     * 查询计数
     *
     * @param query 查询对象
     * @return 文档数
     */
    @Override
    public Long count(Query query) {
        return this.getMongoTemplate().count(query, this.getEntityClass());
    }

    /**
     * @param field 字段名
     * @param value 字段值
     * @return 文档数
     */
    @Override
    public Long countByField(String field, Object value) {
        return this.getMongoTemplate().count(Query.query(Criteria.where(field).is(value)), this.getEntityClass());
    }

    /**
     * @param fieldMap 字段映射
     * @return 文档数
     */
    @Override
    public Long countByFields(Map<String, Object> fieldMap) {
        Query query = new Query();
        fieldMap.forEach((field, value) -> query.addCriteria(Criteria.where(field).is(value)));
        return this.getMongoTemplate().count(query, this.getEntityClass());
    }

    /**
     * 判断是否存在
     *
     * @param query 查询对象
     * @return 是否存在
     */
    @Override
    public Boolean exists(Query query) {
        return this.getMongoTemplate().exists(query, this.getEntityClass());
    }

    /**
     * 根据指定实体id判断是否存在
     *
     * @param id 实体id
     * @return 是否存在
     */
    @Override
    public Boolean existsById(Serializable id) {
        return this.getMongoTemplate().exists(Query.query(Criteria.where("id").is(id)), this.getEntityClass());
    }

    /**
     * @param field 字段名
     * @param value 字段值
     * @return 是否存在
     */
    @Override
    public Boolean existsByField(String field, Object value) {
        return this.getMongoTemplate().exists(Query.query(Criteria.where(field).is(value)), this.getEntityClass());
    }

    /**
     * @param fieldMap 字段映射
     * @return 是否存在
     */
    @Override
    public Boolean existsByFields(Map<String, Object> fieldMap) {
        Query query = new Query();
        fieldMap.forEach((field, value) -> query.addCriteria(Criteria.where(field).is(value)));
        return this.getMongoTemplate().exists(query, this.getEntityClass());
    }
}
