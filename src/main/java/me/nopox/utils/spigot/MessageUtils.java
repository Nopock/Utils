package me.nopox.utils.spigot;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class MessageUtils {
    private TextComponent component;

    public static MessageUtils of(String text) {
        return new MessageUtils(text);
    }

    private MessageUtils(String text) {
        this.component = new TextComponent(CC.translate(text));
    }

    public MessageUtils text(String text) {
        this.component = new TextComponent(CC.translate(text));
        return this;
    }

    public MessageUtils color(ChatColor color) {
        this.component.setColor(color.asBungee());
        return this;
    }

    public MessageUtils clickable(String cmd) {
        this.component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + cmd));
        return this;
    }

    public MessageUtils hover(String text) {
        this.component.setText(CC.translate(text));
        return this;
    }

    public MessageUtils send(Player player){
        player.spigot().sendMessage(this.component);
        return this;
    }
}

