package me.nopox.utils.storage.repository.redis;

import me.nopox.utils.Utils;
import me.nopox.utils.storage.JedisConnection;
import me.nopox.utils.storage.repository.Repository;
import redis.clients.jedis.Jedis;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.CompletableFuture;

/**
 * @author 98ping
 *
 * @param <K> The key that will be used to store the object
 * @param <T> The type of the object that will be stored
 */
public abstract class RedisRepository<K extends String, T> implements Repository<K, T> {
    private final String key;

    public RedisRepository(String key) {
        this.key = key;
    }

    JedisConnection jedis = JedisConnection.getInstance();

    @Override
    public void save(K key, T value) {
        CompletableFuture.runAsync(() -> {
            Jedis jedis = this.jedis.getJedisResource();

            jedis.hset(this.key, key, Utils.getInstance().getGSON().toJson(value));
        });
    }

    @Override
    public CompletableFuture<T> byId(K id) {
        return CompletableFuture.supplyAsync(() -> {
            Jedis jedis = this.jedis.getJedisResource();

            return Utils.getInstance().getGSON().fromJson(jedis.hget(this.key, id), (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
        });
    }

    @Override
    public CompletableFuture<Boolean> exists(K id) {
        return CompletableFuture.supplyAsync(() -> {
            Jedis jedis = this.jedis.getJedisResource();

            return jedis.hexists(this.key, id);
        });
    }
}
