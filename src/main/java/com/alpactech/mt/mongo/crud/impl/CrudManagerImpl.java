package com.alpactech.mt.mongo.crud.impl;

import com.alpactech.mt.mongo.Manager;
import com.alpactech.mt.mongo.crud.CrudManager;
import com.mongodb.bulk.BulkWriteResult;
import org.springframework.data.mongodb.BulkOperationException;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

@Transactional
@Manager
public abstract class CrudManagerImpl<E> extends ViewManagerImpl<E> implements CrudManager<E> {
    @Override
    public E save(E entity) {
        return getMongoTemplate().insert(entity);
    }

    /**
     * @param entityList
     * @return
     */
    @Override
    public List<E> saveBatch(Collection<E> entityList) {
        return getMongoTemplate().insertAll(entityList).stream().toList();
    }

    /**
     * @param entityList 实体列表
     * @param bulkMode
     * @return
     * @throws BulkOperationException
     */
    @Override
    public List<E> saveBatch(Collection<E> entityList, BulkOperations.BulkMode bulkMode) throws BulkOperationException {
        BulkOperations bulkOperations = getMongoTemplate().bulkOps(bulkMode, getEntityClass()).insert(entityList);
        BulkWriteResult bulkWriteResult = bulkOperations.execute();
        List<String> insertedIds = bulkWriteResult.getInserts().stream().map(e -> e.getId().asString().getValue()).toList();
        return getMongoTemplate().find(Query.query(Criteria.where("id").in(insertedIds)), getEntityClass());
    }

    /**
     * @param entity
     * @param update
     * @return
     */
    @Override
    public E update(E entity, Update update) {
        return getMongoTemplate().findAndModify(getQueryByEntity(entity),
                update, FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param query
     * @param entity
     * @return
     */
    @Override
    public E update(Query query, E entity) {
        return getMongoTemplate().findAndModify(query,
                getUpdateByEntity(entity), FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param query
     * @param update
     * @return
     */
    @Override
    public E update(Query query, Update update) {
        return getMongoTemplate().findAndModify(query, update, FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param sourceEntity
     * @param updateEntity
     * @return
     */
    @Override
    public E update(E sourceEntity, E updateEntity) {
        return getMongoTemplate().findAndModify(getQueryByEntity(sourceEntity),
                getUpdateByEntity(updateEntity), FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param id
     * @param update
     * @return
     */
    @Override
    public E updateById(Serializable id, Update update) {
        return getMongoTemplate().findAndModify(getQueryById(id), update, FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param id
     * @param entity
     * @return
     */
    @Override
    public E updateById(Serializable id, E entity) {
        return getMongoTemplate().findAndModify(getQueryById(id),
                getUpdateByEntity(entity), FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param id
     * @param fieldMap
     * @return
     */
    @Override
    public E updateById(Serializable id, Map<String, Object> fieldMap) {
        return getMongoTemplate().findAndModify(getQueryById(id),
                getUpdateByFields(fieldMap), FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public E updateById(E entity) {
        return getMongoTemplate().findAndModify(getQueryByEntity(entity),
                getUpdateByEntity(entity), FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param fieldMap
     * @param update
     * @return
     */
    @Override
    public E updateByFields(Map<String, Object> fieldMap, Update update) {
        return getMongoTemplate().findAndModify(getQueryByFields(fieldMap),
                update, FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param fieldMap
     * @param entity
     * @return
     */
    @Override
    public E updateByFields(Map<String, Object> fieldMap, E entity) {
        return getMongoTemplate().findAndModify(getQueryByFields(fieldMap),
                getUpdateByEntity(entity), FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param entityList
     * @return
     */
    @Override
    public List<E> updateBatchByIds(Collection<E> entityList) {
        return entityList.stream().map(this::updateById).toList();
    }

    /**
     * @param updateMap
     * @return
     */
    @Override
    public List<E> updateBatchByIds(Map<Serializable, Update> updateMap) {
        List<E> list = new ArrayList<>();
        updateMap.forEach((id, u) ->
                list.add(getMongoTemplate().findAndModify(getQueryById(id), u, FIND_AND_MODIFY_OPTIONS, getEntityClass())));
        return list;
    }

    /**
     * @param ids
     * @param update
     * @return
     */
    @Override
    public List<E> updateBatchByIds(Collection<Serializable> ids, Update update) {
        List<E> list = new ArrayList<>();
        ids.forEach(id ->
                list.add(getMongoTemplate().findAndModify(getQueryById(id), update, FIND_AND_MODIFY_OPTIONS, getEntityClass())));
        return list;
    }

    /**
     * @param entity
     * @param fieldMap
     * @return
     */
    @Override
    public E updateFields(E entity, Map<String, Object> fieldMap) {
        return getMongoTemplate().findAndModify(getQueryByEntity(entity),
                getUpdateByFields(fieldMap), FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param id
     * @param field
     * @param value
     * @return
     */
    @Override
    public E updateFieldById(Serializable id, String field, Object value) {
        return getMongoTemplate().findAndModify(getQueryById(id),
                Update.update(field, value), FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param id
     * @param fieldMap
     * @return
     */
    @Override
    public E updateFieldsById(Serializable id, Map<String, Object> fieldMap) {
        return getMongoTemplate().findAndModify(getQueryById(id),
                getUpdateByFields(fieldMap), FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public E saveOrUpdate(E entity) {
        Query query = getQueryByEntity(entity);
        getMongoTemplate().upsert(getQueryByEntity(entity), getUpdateByEntity(entity), getEntityClass());
        return getMongoTemplate().findOne(query, getEntityClass());
    }

    /**
     * @param entityList
     * @return
     */
    @Override
    public List<E> saveOrUpdateBatch(Collection<E> entityList) {
        return entityList.stream().distinct().map(this::saveOrUpdate).toList();
    }

    /**
     * @param query
     * @param field
     * @param element
     * @return
     */
    @Override
    public E addToSet(Query query, String field, Object element) {
        return getMongoTemplate().findAndModify(query, new Update().addToSet(field, element), FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param id
     * @param field
     * @param element
     * @return
     */
    @Override
    public E addToSet(Serializable id, String field, Object element) {
        return getMongoTemplate().findAndModify(getQueryById(id), new Update().addToSet(field, element), FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param entity
     * @param field
     * @param element
     * @return
     */
    @Override
    public E addToSet(E entity, String field, Object element) {
        return getMongoTemplate().findAndModify(getQueryByEntity(entity), new Update().addToSet(field, element), FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param query
     * @param field
     * @param elements
     * @return
     */
    @Override
    public E addAllToSet(Query query, String field, Object... elements) {
        return getMongoTemplate().findAndModify(query, new Update().addToSet(field).each(elements),
                FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param id
     * @param field
     * @param elements
     * @return
     */
    @Override
    public E addAllToSet(Serializable id, String field, Object... elements) {
        return getMongoTemplate().findAndModify(getQueryById(id), new Update().addToSet(field).each(elements),
                FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param entity
     * @param field
     * @param elements
     * @return
     */
    @Override
    public E addAllToSet(E entity, String field, Object... elements) {
        return getMongoTemplate().findAndModify(getQueryByEntity(entity), new Update().addToSet(field).each(elements),
                FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param query
     * @param field
     * @param elements
     * @return
     */
    @Override
    public E addAllToSet(Query query, String field, Collection<Object> elements) {
        return getMongoTemplate().findAndModify(query, new Update().addToSet(field).each(elements),
                FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param id
     * @param field
     * @param elements
     * @return
     */
    @Override
    public E addAllToSet(Serializable id, String field, Collection<Object> elements) {
        return getMongoTemplate().findAndModify(getQueryById(id), new Update().addToSet(field).each(elements),
                FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param entity
     * @param field
     * @param elements
     * @return
     */
    @Override
    public E addAllToSet(E entity, String field, Collection<Object> elements) {
        return getMongoTemplate().findAndModify(getQueryByEntity(entity), new Update().addToSet(field).each(elements),
                FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param query
     * @param field
     * @param element
     * @return
     */
    @Override
    public E push(Query query, String field, Object element) {
        return getMongoTemplate().findAndModify(query, new Update().push(field, element),
                FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param id
     * @param field
     * @param element
     * @return
     */
    @Override
    public E push(Serializable id, String field, Object element) {
        return getMongoTemplate().findAndModify(getQueryById(id), new Update().push(field, element),
                FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param entity
     * @param field
     * @param element
     * @return
     */
    @Override
    public E push(E entity, String field, Object element) {
        return getMongoTemplate().findAndModify(getQueryByEntity(entity), new Update().push(field, element),
                FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param query
     * @param field
     * @param element
     * @return
     */
    @Override
    public E pushAll(Query query, String field, Object... element) {
        return getMongoTemplate().findAndModify(query, new Update().push(field).each(element),
                FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param id
     * @param field
     * @param element
     * @return
     */
    @Override
    public E pushAll(Serializable id, String field, Object... element) {
        return getMongoTemplate().findAndModify(getQueryById(id), new Update().push(field).each(element),
                FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param entity
     * @param field
     * @param element
     * @return
     */
    @Override
    public E pushAll(E entity, String field, Object... element) {
        return getMongoTemplate().findAndModify(getQueryByEntity(entity), new Update().push(field).each(element),
                FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param query
     * @param field
     * @param elements
     * @return
     */
    @Override
    public E pushAll(Query query, String field, Collection<Object> elements) {
        return getMongoTemplate().findAndModify(query, new Update().push(field).each(elements),
                FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param id
     * @param field
     * @param elements
     * @return
     */
    @Override
    public E pushAll(Serializable id, String field, Collection<Object> elements) {
        return getMongoTemplate().findAndModify(getQueryById(id), new Update().push(field).each(elements),
                FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param entity
     * @param field
     * @param elements
     * @return
     */
    @Override
    public E pushAll(E entity, String field, Collection<Object> elements) {
        return getMongoTemplate().findAndModify(getQueryByEntity(entity), new Update().push(field).each(elements),
                FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param query
     * @param field
     * @param element
     * @return
     */
    @Override
    public E pull(Query query, String field, Object element) {
        return getMongoTemplate().findAndModify(query, new Update().pull(field, element), FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param id
     * @param field
     * @param element
     * @return
     */
    @Override
    public E pull(Serializable id, String field, Object element) {
        return getMongoTemplate().findAndModify(getQueryById(id), new Update().pull(field, element), FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param entity
     * @param field
     * @param element
     * @return
     */
    @Override
    public E pull(E entity, String field, Object element) {
        return getMongoTemplate().findAndModify(getQueryByEntity(entity), new Update().pull(field, element), FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param query
     * @param field
     * @param elements
     * @return
     */
    @Override
    public E pullAll(Query query, String field, Object... elements) {
        return getMongoTemplate().findAndModify(query, new Update().pullAll(field, elements), FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param id
     * @param field
     * @param elements
     * @return
     */
    @Override
    public E pullAll(Serializable id, String field, Object... elements) {
        return getMongoTemplate().findAndModify(getQueryById(id), new Update().pullAll(field, elements), FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param entity
     * @param field
     * @param elements
     * @return
     */
    @Override
    public E pullAll(E entity, String field, Object... elements) {
        return getMongoTemplate().findAndModify(getQueryByEntity(entity), new Update().pullAll(field, elements), FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param query
     * @param field
     * @param elements
     * @return
     */
    @Override
    public E pullAll(Query query, String field, Collection<Object> elements) {
        return getMongoTemplate().findAndModify(query, new Update().pullAll(field, elements.toArray()), FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param id
     * @param field
     * @param elements
     * @return
     */
    @Override
    public E pullAll(Serializable id, String field, Collection<Object> elements) {
        return getMongoTemplate().findAndModify(getQueryById(id), new Update().pullAll(field, elements.toArray()), FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param entity
     * @param field
     * @param elements
     * @return
     */
    @Override
    public E pullAll(E entity, String field, Collection<Object> elements) {
        return getMongoTemplate().findAndModify(getQueryByEntity(entity), new Update().pullAll(field, elements.toArray()), FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param query
     * @param field
     * @param position
     * @return
     */
    @Override
    public E pop(Query query, String field, Update.Position position) {
        return getMongoTemplate().findAndModify(query, new Update().pop(field, position),
                FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param id
     * @param field
     * @param position
     * @return
     */
    @Override
    public E pop(Serializable id, String field, Update.Position position) {
        return getMongoTemplate().findAndModify(getQueryById(id), new Update().pop(field, position),
                FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param entity
     * @param field
     * @param position
     * @return
     */
    @Override
    public E pop(E entity, String field, Update.Position position) {
        return getMongoTemplate().findAndModify(getQueryByEntity(entity), new Update().pop(field, position),
                FIND_AND_MODIFY_OPTIONS, getEntityClass());
    }

    /**
     * @param query
     * @param field
     * @return
     */
    @Override
    public E distinct(Query query, String field) {
        return null;
    }

    /**
     * @param id
     * @param field
     * @return
     */
    @Override
    public E distinct(Serializable id, String field) {
        return null;
    }

    /**
     * @param entity
     * @param field
     * @return
     */
    @Override
    public E distinct(E entity, String field) {
        return null;
    }

    /**
     * @param query
     */
    @Override
    public void remove(Query query) {
        getMongoTemplate().remove(query, getEntityClass());
    }

    /**
     * @param entity
     */
    @Override
    public void remove(E entity) {
        getMongoTemplate().remove(getQueryByEntity(entity), getEntityClass());
    }

    /**
     * @param id
     */
    @Override
    public void removeById(Serializable id) {
        getMongoTemplate().remove(getQueryById(id), getEntityClass());
    }

    /**
     * @param entity
     */
    @Override
    public void removeById(E entity) {
        getMongoTemplate().remove(getQueryByEntity(entity), getEntityClass());
    }

    /**
     * @param field
     * @param value
     */
    @Override
    public void removeByField(String field, Object value) {
        getMongoTemplate().remove(getQueryByField(field, value), getEntityClass());
    }

    /**
     * @param fieldMap
     */
    @Override
    public void removeByFields(Map<String, Object> fieldMap) {
        getMongoTemplate().remove(getQueryByFields(fieldMap), getEntityClass());
    }

    /**
     * @param idList
     */
    @Override
    public void removeByIds(Collection<? extends Serializable> idList) {
        getMongoTemplate().remove(Query.query(Criteria.where("_id").in(idList)), getEntityClass());
    }
}
