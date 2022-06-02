package me.nopox.utils.discord.cache.message;

import me.nopox.utils.storage.repository.redis.RedisRepository;
import net.dv8tion.jda.api.entities.Message;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MessageCache extends RedisRepository<String, CachedMessage> {

    /**
     * This initiates the cache (Cache expires 12 hours after)
     */
    public MessageCache() {
        super("message-cache", CachedMessage.class);
    }
}
