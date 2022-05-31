package me.nopox.utils.storage.repository;

import java.util.concurrent.CompletableFuture;

/**
 * @author 98ping
 */
public interface Repository<K, T> {

    void save(K key, T value);

    CompletableFuture<T> byId(K id);

    CompletableFuture<Boolean> exists(K id);

    void delete(K id);

    void deleteAll();

    CompletableFuture<List<T>> getAll();
    
    CompletableFuture<T> byField(String field, K id);
}
