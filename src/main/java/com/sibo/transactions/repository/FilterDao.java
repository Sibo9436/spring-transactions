package com.sibo.transactions.repository;

import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public interface FilterDao<T,ID> extends CrudDao<T,ID> {
    List<T> getAll();
    List<T> getFiltered(T filter);
}
