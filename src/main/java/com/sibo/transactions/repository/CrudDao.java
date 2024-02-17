package com.sibo.transactions.repository;

import java.util.Optional;

public interface CrudDao<T,ID> {
    Optional<T> get(ID id);
    void create(T newT);
    void update(T newT);
    void delete(ID id);

}
