package me.nopox.utils.spigot;

import org.bukkit.ChatColor;

import java.util.List;

public class CC {

    public static String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String[] translate(String[] text) {
        for (int i = 0; i < text.length; i++) {
            text[i] = translate(text[i]);
        }
        return text;
    }

    public static List<String> translate(List<String> text) {
        for (int i = 0; i < text.size(); i++) {
            text.set(i, translate(text.get(i)));
        }
        return text;
    }
}
