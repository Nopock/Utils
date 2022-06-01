package me.nopox.utils.discord;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.command.SlashCommand;
import lombok.Getter;
import me.nopox.utils.discord.cache.message.MessageCache;
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

    private JDABuilder jdaBuilder;

    private EnumSet<GatewayIntent> intents;

    private CommandClientBuilder commandClientBuilder;

    private CommandClient commandClient;

    @Getter private static boolean messageCache;
    @Getter private static MessageCache messageCacheInstance;

    /**
     * This initiates the discord bot
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

        this.jdaBuilder = JDABuilder.createLight(token, intents)
                .setStatus(onlineStatus)
                .setActivity(activity)
                .enableCache(CacheFlag.ONLINE_STATUS, CacheFlag.MEMBER_OVERRIDES, CacheFlag.CLIENT_STATUS)
                .addEventListeners(commandClient);

    }

    /**
     * This registers a non slash command to the bot
     * 
     * @param command The command to register
     */
    public DiscordBot registerCommand(Command command) {
        this.commandClientBuilder.addCommand(command);

        return this;
    }

    /**
     * This registers a slash command to the bot
     *
     * @param command The command to register
     */
    public DiscordBot registerCommand(SlashCommand command) {
        this.commandClientBuilder.addSlashCommand(command);

        return this;
    }

    /**
     * This adds listeners to the bot
     *
     * @param listeners
     */
    public DiscordBot registerListener(Object... listeners) {
        this.jda.addEventListener(listeners);

        return this;
    }

    /**
     * After this is called registerCommand and registerListener will not work
     */
    public DiscordBot build() throws LoginException {
        this.commandClient = this.commandClientBuilder.build();
        this.jdaBuilder.addEventListeners(commandClient);
        this.jda = jdaBuilder.build();

        this.jda.updateCommands().queue();

        return this;
    }


    public DiscordBot enableMessageCache() {
        this.messageCache = true;
        return this;
    }




}
