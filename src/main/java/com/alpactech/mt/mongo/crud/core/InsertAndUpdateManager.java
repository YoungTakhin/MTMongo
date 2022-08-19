package com.alpactech.mt.mongo.crud.core;

import java.util.Collection;
import java.util.List;

public interface InsertAndUpdateManager<E> extends InsertManager<E>, UpdateManager<E> {
    E saveOrUpdate(E entity);

    List<E> saveOrUpdateBatch(Collection<E> entityList);
}
