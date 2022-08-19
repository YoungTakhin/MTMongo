package com.alpactech.mt.mongo.crud.core;

import org.springframework.data.mongodb.BulkOperationException;
import org.springframework.data.mongodb.core.BulkOperations;

import java.util.Collection;
import java.util.List;

public interface InsertManager<E> {
    E save(E entity);

    List<E> saveBatch(Collection<E> entityList);

    /**
     * 批量新增
     *
     * @param entityList 实体列表
     * @return 新增后的实体列表
     */
    List<E> saveBatch(Collection<E> entityList, BulkOperations.BulkMode bulkMode) throws BulkOperationException;
}
