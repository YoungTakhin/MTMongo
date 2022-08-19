package com.alpactech.mt.mongo.base;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class AbstractMongoTemplateGetter<E> {
    @Autowired
    private MongoTemplate mongoTemplate;

    protected MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    @SuppressWarnings("unchecked")
    protected Class<E> getEntityClass() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<E>) parameterizedType.getActualTypeArguments()[0];
    }

    /**
     * 将指定的实体对象转换为Query
     *
     * @param entity 给定的实体对象
     * @return Query
     */
    protected Query getQueryByEntity(E entity) {
        return Query.query(Criteria.where("_id").is(getValueByFieldName("id", entity)));
    }

    /**
     * 将指定的id转换为Query
     * @param id id
     * @return Query
     */
    protected Query getQueryById(Serializable id) {
        return Query.query(Criteria.where("_id").is(id));
    }

    /**
     * 将指定的实体属性转换为Query
     * @param field 属性名
     * @param value 属性值
     * @return Query
     */
    protected Query getQueryByField(String field, Object value) {
        return Query.query(Criteria.where(field).is(value));
    }

    /**
     * 将指定的实体属性转换为Query
     * @param fieldMap 属性
     * @return Query
     */
    protected Query getQueryByFields(Map<String, Object> fieldMap) {
        Assert.notEmpty(fieldMap, "fieldMap is empty");
        Query query = new Query();
        fieldMap.forEach((field, value) -> query.addCriteria(Criteria.where(field).is(value)));
        return query;
    }

    /**
     * 将指定的实体对象转换为Update, 不包括id
     *
     * @param entity 给定的实体对象
     * @return Update
     */
    protected Update getUpdateByEntity(E entity) {
        Update update = new Update();
        getFieldName(entity).forEach(fieldName -> {
            Object fieldValue = getValueByFieldName(fieldName, entity);
            if (fieldValue != null) {
                update.set(fieldName, fieldValue);
            }
        });
        return update;
    }

    /**
     * 将指定的属性转换为Update, 不包括id
     * @param fieldMap
     * @return
     */
    protected Update getUpdateByFields(Map<String, Object> fieldMap) {
        Update update = new Update();
        fieldMap.remove("id");
        fieldMap.remove("serialVersionUID");
        fieldMap.forEach((fieldName, fieldValue) -> {
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
    private List<String> getFieldName(E entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields()).map(Field::getName)
                .filter(f -> !(f.equals("serialVersionUID") || f.equals("id"))).toList();
    }

    /**
     * 根据给定的实体对象属性名获取属性值
     * @param fieldName 属性名
     * @param entity 给定的实体对象
     * @return 属性值
     */
    @SneakyThrows
    private Object getValueByFieldName(String fieldName, E entity) {
            String e = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + e + fieldName.substring(1);
            return entity.getClass().getMethod(getter).invoke(entity);
    }
}
