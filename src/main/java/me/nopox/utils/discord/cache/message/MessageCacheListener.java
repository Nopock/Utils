package me.nopox.utils.discord.cache.message;

import me.nopox.utils.discord.DiscordBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageCacheListener extends ListenerAdapter {

    public void onMessage(MessageReceivedEvent event) {
        if (!DiscordBot.isMessageCache()) return;

        DiscordBot.getMessageCacheInstance().saveExpireable(event.getMessage().getId(), event.getMessage(), 60 * 60 * 24 * 7);
    }
}
