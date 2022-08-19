package com.alpactech.mt.mongo;

import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Deprecated(since = "1.1.0")
@Manager
public interface BaseMongoManager<E> {
    MongoTemplate getMongoTemplate();

    E save(E entity);

    /**
     * 批量新增
     * @param entityList 实体列表
     * @return 新增后的实体列表
     */
    List<E> saveBatch(List<E> entityList);

    Collection<E> saveOrUpdateBatch(Collection<E> entityList);

    Collection<E> saveOrUpdateBatch(Collection<E> entityList, int batchSize);

    void removeById(Serializable id);

    void removeById(E entity);

    void remove(Query query);

    void remove(E entity);

    void removeByIds(Collection<? extends Serializable> idList);

    E updateById(Serializable id, Update update);

    E updateById(Serializable id, E entity);

    E updateById(E entity);

    E update(Query query, Update update);

    E update(E entity, Update update);

    E update(Query query, E entity);

    Collection<E> updateBatchById(Collection<E> entityList);

    Collection<E> updateBatchById(Collection<E> entityList, int batchSize);

    E saveOrUpdate(E entity);

    E getById(Serializable id);

    List<E> listByIds(Collection<? extends Serializable> idList);

    List<E> listByMap(Map<String, Object> columnMap);

    E getOne(Query query);
//
//    Map<String, Object> getMap(Wrapper<E> queryWrapper);
//
//    <V> V getObj(Wrapper<E> queryWrapper, Function<? super Object, V> mapper);
//
    Long count();

    Long count(Query query);

    List<E> list(Query query);

    List<E> list();

    List<E> page(Query query, Integer pageNum, Integer pageSize);

    List<E> page(Integer pageNum, Integer pageSize);

    List<E> page(Integer pageNum, Integer pageSize, String... sortBy);

    List<E> page(Query query, Integer pageNum, Integer pageSize, String... sortBy);

    List<E> page(Integer pageNum, Integer pageSize, String orderBy, String... sortBy);

    List<E> page(Query query, Integer pageNum, Integer pageSize, String orderBy, String... sortBy);
//
//    List<Map<String, Object>> listMaps(Wrapper<E> queryWrapper);
//
//    List<Map<String, Object>> listMaps();
//
//    List<Object> listObjs();
//
//    <V> List<V> listObjs(Function<? super Object, V> mapper);
//
//    List<Object> listObjs(Wrapper<E> queryWrapper);
//
//    <V> List<V> listObjs(Wrapper<E> queryWrapper, Function<? super Object, V> mapper);
//
//    <P extends IPage<Map<String, Object>>> P pageMaps(P page, Wrapper<E> queryWrapper);
//
//    <P extends IPage<Map<String, Object>>> P pageMaps(P page);

//    MongoRepository<E, ? extends Serializable> getBaseMapper();
//
//    Class<E> getEntityClass();

//    default QueryChainWrapper<E> query() {
//        return ChainWrappers.queryChain(this.getBaseMapper());
//    }
//
//    default LambdaQueryChainWrapper<E> lambdaQuery() {
//        return ChainWrappers.lambdaQueryChain(this.getBaseMapper());
//    }
//
//    default KtQueryChainWrapper<E> ktQuery() {
//        return ChainWrappers.ktQueryChain(this.getBaseMapper(), this.getEntityClass());
//    }
//
//    default KtUpdateChainWrapper<E> ktUpdate() {
//        return ChainWrappers.ktUpdateChain(this.getBaseMapper(), this.getEntityClass());
//    }
//
//    default UpdateChainWrapper<E> update() {
//        return ChainWrappers.updateChain(this.getBaseMapper());
//    }
//
//    default LambdaUpdateChainWrapper<E> lambdaUpdate() {
//        return ChainWrappers.lambdaUpdateChain(this.getBaseMapper());
//    }
//
    E saveOrUpdate(E entity, Query query);

    Boolean exists(Query query);

    Boolean existsById(Serializable id);

    Boolean existsByIndex(String index, Object value);

    Document aggregate(Aggregation aggregation);

    <O> O aggregate(Aggregation aggregation, Class<O> outEntity);

    List<E> listAggregate(Aggregation aggregation);

//    <O> List<O> listAggregate(Aggregation aggregation, Class<O> outEntity);

    List<BasicDBObject> pageAggregate(Aggregation aggregation, String orderBy, String... sortBy);

    <O> List<O> pageAggregate(Aggregation aggregation, Class<O> outEntity, String orderBy, String... sortBy);
}
