package me.nopox.utils.discord.cache.message;

import lombok.Data;
import net.dv8tion.jda.api.entities.ChannelType;

@Data
public class CachedMessage {
  public String author;
  public String content;
  public long timeSent;
  public String guild;
  public String channel;
  public ChannelType channelType;
}
