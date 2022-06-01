package me.nopox.utils.discord.cache.message;

import me.nopox.utils.discord.DiscordBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageCacheListener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        if (!DiscordBot.isMessageCache()) return;
        
        CachedMessage m = new CachedMessage();
        
        m.setAuthor(event.getMember().getId());
        m.setContent(event.getMessage().getContentRaw());
        m.setTimeSent(System.currenttimemillis());
        m.setGuild(event.getGuild().getId());
        m.setChannel(event.getChannel().getId());
        m.setChannelType(event.getChannel().getType());

        DiscordBot.getMessageCacheInstance().saveExpireable(event.getMessage().getId(), m, 60 * 60 * 24 * 7);
    }
    
    //TODO Remove it from redis after it has been deleted. Not sure how I will handle that ngl... might have to make the user remove it from the cache because if I just remove it here then I believe when they try and access it then it will be null...
}
