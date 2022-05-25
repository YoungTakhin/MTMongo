package com.alpactech.mt.mongo.base;

import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Manager
public abstract class BaseMongoManagerImpl<E> implements BaseMongoManager<E> {
    @Autowired
    private MongoTemplate mongoTemplate;

    private final Class<E> entityClass;

    @SuppressWarnings("unchecked")
    protected BaseMongoManagerImpl() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        entityClass = (Class<E>) parameterizedType.getActualTypeArguments()[0];
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    /**
     * 判断数据库是否操作成功
     * @param result 数据库操作结果
     * @return 如果影响行数大于1则成功，否则失败
     */
    private static Boolean retBool(Long result) {
        return result > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public E save(E entity) {
        return mongoTemplate.insert(entity);
    }

    @Override
    public List<E> saveBatch(List<E> entityList) {
        return mongoTemplate.insertAll(entityList).stream().toList();
    }

    @Override
    public Collection<E> saveOrUpdateBatch(Collection<E> entityList) {
        return null;
    }

    @Override
    public Collection<E> saveOrUpdateBatch(Collection<E> entityList, int batchSize) {
        return null;
    }

    @Override
    public void removeById(Serializable id) {
        mongoTemplate.findAndRemove(Query.query(Criteria.where("_id").is(id)), entityClass);
    }

    @Override
    public void removeById(E entity) {
        mongoTemplate.remove(this.getQueryByEntity(entity), entityClass);
    }

    @Override
    public void remove(Query query) {
        mongoTemplate.remove(query, entityClass);
    }

    @Override
    public void remove(E entity) {
        mongoTemplate.remove(entity);
    }

    @Override
    public void removeByIds(Collection<? extends Serializable> idList) {
        mongoTemplate.remove(Query.query(Criteria.where("_id").in(idList)));
    }

    @Override
    public E updateById(Serializable id, Update update) {
        return mongoTemplate.findAndModify(Query.query(Criteria.where("_id").is(id)), update,
                FindAndModifyOptions.options().returnNew(true), entityClass);
    }

    @Override
    public E updateById(Serializable id, E entity) {
        return mongoTemplate.findAndModify(Query.query(Criteria.where("_id").is(id)), getUpdateByEntity(entity),
                FindAndModifyOptions.options().returnNew(true), entityClass);
    }

    @Override
    public E updateById(E entity) {
        return mongoTemplate.findAndModify(getQueryByEntity(entity), getUpdateByEntity(entity),
                FindAndModifyOptions.options().returnNew(true), entityClass);
    }

    @Override
    public E update(Query query, Update update) {
        return mongoTemplate.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true), entityClass);
    }

    @Override
    public E update(E entity, Update update) {
        return mongoTemplate.findAndModify(this.getQueryByEntity(entity), update,
                FindAndModifyOptions.options().returnNew(true), entityClass);
    }

    @Override
    public E update(Query query, E entity) {
        return mongoTemplate.findAndModify(
                query, getUpdateByEntity(entity), FindAndModifyOptions.options().returnNew(true), entityClass);
    }

    // TODO: 2021/3/25 可能存在bug
    @Override
    public Collection<E> updateBatchById(Collection<E> entityList) {
        return entityList.stream().map(e -> mongoTemplate.findAndModify(getQueryByEntity(e), getUpdateByEntity(e),
                FindAndModifyOptions.options().returnNew(true), entityClass)).collect(Collectors.toList());
//        entityList.forEach(entity ->
//                mongoTemplate.findAndModify(getQueryByEntity(entity),
//                        getUpdateByEntity(entity), FindAndModifyOptions.options().returnNew(true), entityClass));
//        return mongoTemplate.findAndModify();
    }

    @Override
    public Collection<E> updateBatchById(Collection<E> entityList, int batchSize) {
        return null;
    }

    @Override
    public E saveOrUpdate(E entity) {
        return !retBool(mongoTemplate.upsert(getQueryByEntity(entity), getUpdateByEntity(entity), entityClass)
                .getModifiedCount()) ? null : mongoTemplate.findOne(getQueryByEntity(entity), entityClass);
    }

    @Override
    public E getById(Serializable id) {
        return mongoTemplate.findById(id, entityClass);
    }

    @Override
    public List<E> listByIds(Collection<? extends Serializable> idList) {
        return mongoTemplate.find(Query.query(Criteria.where("_id").in(idList)), entityClass);
    }

    @Override
    public List<E> listByMap(Map<String, Object> columnMap) {
        return null;
    }

    @Override
    public E getOne(Query query) {
        return mongoTemplate.findOne(query, entityClass);
    }

    @Override
    public Long count() {
        return mongoTemplate.count(new Query(), entityClass);
    }

    @Override
    public Long count(Query query) {
        return mongoTemplate.count(query, entityClass);
    }

    @Override
    public List<E> list(Query query) {
        return mongoTemplate.find(query, entityClass);
    }

    @Override
    public List<E> list() {
        return mongoTemplate.findAll(entityClass);
    }

    @Override
    public List<E> page(Query query, Integer pageNum, Integer pageSize) {
        return mongoTemplate.find(query.with(PageRequest.of(pageNum, pageSize)), entityClass);
    }

    @Override
    public List<E> page(Integer pageNum, Integer pageSize) {
        return mongoTemplate.find(new Query().with(PageRequest.of(pageNum, pageSize)), entityClass);
    }

    @Override
    public List<E> page(Integer pageNum, Integer pageSize, String... sortBy) {
        return mongoTemplate.find(new Query().with(PageRequest.of(pageNum, pageSize)
                .withSort(Sort.by(sortBy))), entityClass);
    }

    @Override
    public List<E> page(Query query, Integer pageNum, Integer pageSize, String... sortBy) {
        return mongoTemplate.find(query.with(PageRequest.of(pageNum, pageSize)
                .withSort(Sort.by(sortBy))), entityClass);
    }

    @Override
    public List<E> page(Integer pageNum, Integer pageSize, String orderBy, String... sortBy) {
        return mongoTemplate.find(new Query().with(PageRequest.of(pageNum, pageSize)
                .withSort(Sort.by(Sort.Direction.fromString(orderBy), sortBy))), entityClass);
    }

    @Override
    public List<E> page(Query query, Integer pageNum, Integer pageSize, String orderBy, String... sortBy) {
        return mongoTemplate.find(query.with(PageRequest.of(pageNum, pageSize)
                .withSort(Sort.by(Sort.Direction.fromString(orderBy), sortBy))), entityClass);
    }

    @Override
    public E saveOrUpdate(E entity, Query query) {
        if (!retBool(mongoTemplate.upsert(query, getUpdateByEntity(entity), entityClass).getModifiedCount())) {
            return null;
        }
        return mongoTemplate.findOne(getQueryByEntity(entity), entityClass);
    }

    @Override
    public Boolean exists(Query query) {
        return mongoTemplate.exists(query, entityClass);
    }

    @Override
    public Boolean existsById(Serializable id) {
        return mongoTemplate.exists(Query.query(Criteria.where("_id").is(id)), entityClass);
    }

    @Override
    // TODO: 2022/2/25 可能存在bug
    public Boolean existsByIndex(String index, Object value) {
        return mongoTemplate.exists(getQueryByIndex(index, value), entityClass);
    }

    public Document aggregate(Aggregation aggregation) {
        return mongoTemplate.aggregate(aggregation, entityClass, BasicDBObject.class).getRawResults();
    }

    public <O> O aggregate(Aggregation aggregation, Class<O> outEntity) {
        return mongoTemplate.aggregate(aggregation, entityClass, outEntity).getUniqueMappedResult();
    }

//    @Override
//    public List<BasicDBObject> listAggregate(Aggregation aggregation) {
//        return mongoTemplate.aggregate(aggregation, entityClass, BasicDBObject.class).getMappedResults();
//    }

    @Override
    public List<E> listAggregate(Aggregation aggregation) {
        System.out.println(entityClass);
        System.out.println(mongoTemplate.aggregate(aggregation, entityClass, entityClass).getRawResults());
        return mongoTemplate.aggregate(aggregation, entityClass, entityClass).getMappedResults();
    }

    @Override
    public List<BasicDBObject> pageAggregate(Aggregation aggregation, String orderBy, String... sortBy) {
        return mongoTemplate.aggregate(aggregation.withOptions(
                Aggregation.newAggregation(
                        Aggregation.sort(Sort.Direction.fromString(orderBy), sortBy)).getOptions()),
                entityClass, BasicDBObject.class).getMappedResults();
    }

    @Override
    public <O> List<O> pageAggregate(Aggregation aggregation, Class<O> outEntity, String orderBy, String... sortBy) {
        return mongoTemplate.aggregate(
                aggregation.withOptions(
                        Aggregation.newAggregation(
                                Aggregation.sort(Sort.Direction.fromString(orderBy), sortBy)).getOptions()),
                entityClass, outEntity).getMappedResults();
    }

    /**
     * 将指定的实体对象转换为Query
     *
     * @param entity 给定的实体对象
     * @return Query
     */
    private Query getQueryByEntity(E entity) {
        return Query.query(Criteria.where("_id").is(getFieldValueByName("id", entity)));
    }

    /**
     * 将指定的实体索引对象转换为Query
     * @param index 索引名
     * @param value 索引值
     * @return Query
     */
    // TODO: 2022/2/25 可能存在bug
    private Query getQueryByIndex(String index, Object value) {
        return Query.query(Criteria.where(index).is(value));
    }

    /**
     * 将指定的实体对象转换为Update, 不包括id
     *
     * @param entity 给定的实体对象
     * @return Update
     */
    private Update getUpdateByEntity(E entity) {
        Update update = new Update();
        getFiledName(entity).forEach(fieldName -> {
            Object fieldValue = getFieldValueByName(fieldName, entity);
            if (fieldValue != null) {
                update.set(fieldName, fieldValue);
            }
        });
        return update;
    }

    /**
     * 获取给定实体对象的属性名列表
     * @param entity 给定的实体对象
     * @return 属性名列表
     */
    private List<String> getFiledName(E entity) {
        List<Field> fields = Arrays.asList(entity.getClass().getDeclaredFields().clone());
        List<String> fieldNames = fields.stream().map(Field::getName).collect(Collectors.toList());
        fieldNames.remove("serialVersionUID");
        fieldNames.remove("id");
        return fieldNames;
    }

    /**
     * 根据给定的实体对象属性名获取属性值
     * @param fieldName 属性名
     * @param entity 给定的实体对象
     * @return 属性值
     */
    private Object getFieldValueByName(String fieldName, E entity) {
        try {
            String e = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + e + fieldName.substring(1);
            return entity.getClass().getMethod(getter).invoke(entity);
        } catch (Exception var6) {
            return null;
        }
    }
}
