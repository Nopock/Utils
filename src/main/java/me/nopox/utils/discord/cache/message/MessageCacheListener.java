package me.nopox.utils.discord.cache.message;

import me.nopox.utils.discord.DiscordBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageCacheListener extends ListenerAdapter {

    public void onMessage(MessageReceivedEvent event) {
        if (!DiscordBot.isMessageCache()) return;

        DiscordBot.getMessageCacheInstance().saveExpireable(event.getMessage().getId(), event.getMessage(), 60 * 60 * 24 * 7);
    }
    
    //TODO Remove it from redis after it has been deleted. Not sure how I will handle that ngl... might have to make the user remove it from the cache because if I just remove it here then I believe when they try and access it then it will be null...
}
