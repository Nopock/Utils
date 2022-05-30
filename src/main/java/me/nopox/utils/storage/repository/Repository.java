package me.nopox.utils.storage.repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Repository<K, T> {

    void save(K key, T value);

    CompletableFuture<T> byId(K id);

    boolean exists(K id);

}
