package me.nopox.utils.storage.repository;

import java.util.concurrent.CompletableFuture;

/**
 * @author 98ping
 */
public interface Repository<K, T> {

    void save(K key, T value);

    CompletableFuture<T> byId(K id);

    CompletableFuture<Boolean> exists(K id);

}
