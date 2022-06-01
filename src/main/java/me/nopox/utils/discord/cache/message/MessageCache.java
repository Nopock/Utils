package me.nopox.utils.discord.cache.message;

import me.nopox.utils.storage.repository.redis.RedisRepository;
import net.dv8tion.jda.api.entities.Message;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MessageCache extends RedisRepository<String, Message> {

    /**
     * This initiates the cache (Cache expires 7 days after)
     */
    public MessageCache() {
        super("message-cache", Message.class);
    }

    @Override
    /**
     * Don't use this yet
     */
    @Deprecated
    public CompletableFuture<List<Message>> getAll() {
        return null;
    }

    @Override
    /**
     * Don't use this (Doesn't work for redis since redis is K, V)
     */
    @Deprecated
    public CompletableFuture<Message> byKey(String field, String id) {
        return null;
    }
}
