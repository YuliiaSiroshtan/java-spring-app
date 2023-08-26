package com.api.gateway.service;

import java.util.Iterator;

public interface GatewayService<T> {
    Iterator<T> getAll(int size, int page);
    T getOne(Integer id);
    T save(T object);
    T update(T object);
    Boolean delete(Integer id);
    Iterator<T> find(String field);
}
