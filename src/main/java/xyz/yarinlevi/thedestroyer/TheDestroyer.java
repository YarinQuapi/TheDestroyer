package xyz.yarinlevi.thedestroyer;

import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.GatewayIntent;
import xyz.yarinlevi.thedestroyer.listeners.DestroyerCommands;
import xyz.yarinlevi.thedestroyer.config.Configuration;
import xyz.yarinlevi.thedestroyer.listeners.ReadyListener;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;

public class TheDestroyer {
    @Getter private TheDestroyer instance;
    @Getter private JDA jda;
    @Getter private User user;

    @Getter private DestroyerCommands commandHandler;

    @Getter private Configuration config;

    public TheDestroyer start(String[] args) throws InterruptedException, LoginException, SQLException {
        instance = this;

        config = new Configuration("config.yml",1);

        JDABuilder builder = JDABuilder.createDefault(config.getString("token"));

        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);

        builder.setActivity(Activity.playing("DestroyerOS v0.2"));

        builder.setStatus(OnlineStatus.ONLINE);

        commandHandler = new DestroyerCommands();
        commandHandler.loadCommands();

        //builder.addEventListeners(new BanListener(), commandHandler);

        jda = builder.addEventListeners(new ReadyListener(), commandHandler).build();

        jda.awaitReady();

        user = jda.getSelfUser();

        return instance;
    }
}
