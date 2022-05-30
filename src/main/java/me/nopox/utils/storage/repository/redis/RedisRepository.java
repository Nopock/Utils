package me.nopox.utils.storage.repository.redis;

import me.nopox.utils.storage.repository.Repository;

import java.util.concurrent.CompletableFuture;

public abstract class RedisRepository<K extends String, T extends Class<T>> implements Repository<K, T> {

    //TODO: Implement

    @Override
    public void save(K key, T value) {

    }

    @Override
    public CompletableFuture<T> byId(K id) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> exists(K id) {
        return false;
    }
}
