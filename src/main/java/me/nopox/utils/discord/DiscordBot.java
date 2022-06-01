package me.nopox.utils.discord;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.command.SlashCommand;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

@Getter
public class DiscordBot {

    private JDA jda;

    private EnumSet<GatewayIntent> intents;

    private CommandClientBuilder commandClientBuilder;

    private CommandClient commandClient;

    /**
     *
     * @param onlineStatus The status of the bot (Ex. OnlineStatus.ONLINE)
     * @param activity The bots activity (Ex. Activity.watching("test"))
     * @param ownerId The ID of the owner of the bot
     * @param guildId The guild id that the bot instantly updates slash commands to
     * @param prefix The prefix of the bot (For non slash commands)
     * @param token The bot token
     */
    public DiscordBot(OnlineStatus onlineStatus, Activity activity, String ownerId, String guildId, String prefix, String token) throws LoginException {
        this.intents = EnumSet.of(
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_BANS,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.DIRECT_MESSAGES,
                GatewayIntent.GUILD_PRESENCES
        );

        this.commandClientBuilder = new CommandClientBuilder()
                .setActivity(activity)
                .setStatus(onlineStatus)
                .setPrefix(prefix)
                .setOwnerId(ownerId)
                .forceGuildOnly(guildId);

        this.commandClient = commandClientBuilder.build();

        this.jda = JDABuilder.createLight(token, intents)
                .setStatus(onlineStatus)
                .setActivity(activity)
                .enableCache(CacheFlag.ONLINE_STATUS, CacheFlag.MEMBER_OVERRIDES, CacheFlag.CLIENT_STATUS)
                .addEventListeners(commandClient)
                .build();

        this.jda.updateCommands().queue();

    }

    public DiscordBot registerCommand(Command command) {
        this.commandClient.addCommand(command);

        return this;
    }

    public DiscordBot registerCommand(SlashCommand command) {
        this.commandClient.addSlashCommand(command);

        return this;
    }

    public DiscordBot registerListener(Object listeners) {
        this.jda.addEventListener(listeners);

        return this;
    }
}
