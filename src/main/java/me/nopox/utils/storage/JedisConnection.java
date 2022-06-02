package me.nopox.utils.storage;

import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.net.URI;
import java.net.URISyntaxException;

@Getter
public class JedisConnection {

    @Getter private static JedisConnection instance;

    private final Jedis jedisResource;
    private final JedisPool jedisPool;

    public JedisConnection(String uri) throws URISyntaxException {
        this.jedisPool = new JedisPool(new URI(uri));
        this.jedisResource = jedisPool.getResource();
        jedisResource.connect();
        System.out.println("[Jedis] Connected to " + uri);

        instance = this;
    }
}


