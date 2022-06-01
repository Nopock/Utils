package me.nopox.utils.discord.embed;

import java.awt.*;

public class EmbedBuilder {
    private net.dv8tion.jda.api.EmbedBuilder eb;

    public EmbedBuilder(String title, Color color) {
        eb.setColor(color);
        eb.setTitle(title);
    }

    public EmbedBuilder setDescription(String description) {
        this.eb.setDescription(description);

        return this;
    }

    public EmbedBuilder setFooter(String text, String iconURL) {
        this.eb.setFooter(text, iconURL);

        return this;
    }


}
