package me.nopox.utils.storage.repository.redis;

import me.nopox.utils.Utils;
import me.nopox.utils.storage.JedisConnection;
import me.nopox.utils.storage.repository.Repository;
import org.apache.commons.pool2.PooledObject;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author 98ping
 *
 * @param <K> The key that will be used to store the object
 * @param <T> The type of the object that will be stored
 */
public abstract class RedisRepository<K extends String, T> implements Repository<K, T> {
    private final String key;
    private final Class<T> type;

    public RedisRepository(String key, Class<T> type) {
        this.key = key;
        this.type = type;
    }

    JedisConnection jedis = JedisConnection.getInstance();

    @Override
    public void save(K key, T value) {
        long firstTime = System.currentTimeMillis();
        CompletableFuture.runAsync(() -> {
            Jedis jedis = this.jedis.getJedisResource();

            jedis.hset(this.key, key, Utils.getInstance().getGSON().toJson(value));

            System.out.println("[Redis] Saved object to redis: " + key + " in " + (System.currentTimeMillis() - firstTime) + "ms.");
        });
    }

    @Override
    public CompletableFuture<T> byId(K id) {
        long firstTime = System.currentTimeMillis();
        return CompletableFuture.supplyAsync(() -> {
            Jedis jedis = this.jedis.getJedisResource();

            System.out.println("[Redis] Fetched object from redis: " + id + " in " + (System.currentTimeMillis() - firstTime) + "ms.");
            return Utils.getInstance().getGSON().fromJson(jedis.hget(this.key, id), type);
        });
    }

    @Override
    public CompletableFuture<Boolean> exists(K id) {
        return CompletableFuture.supplyAsync(() -> {
            Jedis jedis = this.jedis.getJedisResource();

            return jedis.hexists(this.key, id);
        });
    }

    @Override
    public void delete(K id) {
        long firstTime = System.currentTimeMillis();
        CompletableFuture.runAsync(() -> {
            Jedis jedis = this.jedis.getJedisResource();

            jedis.hdel(this.key, id);

            System.out.println("[Redis] Deleted object from redis: " + id + " in " + (System.currentTimeMillis() - firstTime) + "ms.");
        });
    }

    @Override
    public void deleteAll() {
        CompletableFuture.runAsync(() -> {
            Jedis jedis = this.jedis.getJedisResource();

            jedis.del(this.key);
        });
    }
    
    @Override
    @Deprecated
    public CompletableFuture<T> byKey(String field, K id) {
        return null;
    }
    
    @Override
    public CompletableFuture<List<T>> getAll() {
        return null;
    }

    public void saveExpireable(K key, T value, long seconds) {
        CompletableFuture.runAsync(() -> {
            Jedis jedis = this.jedis.getJedisResource();

            jedis.hset(this.key, key, Utils.getInstance().getGSON().toJson(value));
            jedis.expire(this.key, seconds);
        });
    }
    
}
