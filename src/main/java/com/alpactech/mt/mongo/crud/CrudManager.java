package com.alpactech.mt.mongo.crud;

import com.alpactech.mt.mongo.crud.core.ReadManager;

public interface CrudManager<E> extends ReadManager<E>, WriteManager<E> {
}
