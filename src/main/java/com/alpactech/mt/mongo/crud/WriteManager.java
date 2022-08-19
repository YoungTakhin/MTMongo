package com.alpactech.mt.mongo.crud;

import com.alpactech.mt.mongo.crud.core.InsertAndUpdateManager;
import com.alpactech.mt.mongo.crud.core.RemoveManager;

public interface WriteManager<E> extends InsertAndUpdateManager<E>, RemoveManager<E> {
}
