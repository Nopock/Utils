package me.nopox.utils.spigot;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CC {


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

    public static String translate(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder("");
            for (char c : ch) {
                builder.append("&").append(c);
            }

            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
