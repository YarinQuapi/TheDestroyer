package xyz.yarinlevi.thedestroyer;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import xyz.yarinlevi.thedestroyer.listeners.MessageListener;
import xyz.yarinlevi.thedestroyer.listeners.ReadyListener;

import javax.security.auth.login.LoginException;

public class Main {
    private static final String TOKEN = "token";
    private static JDA instance;
    private static User user;

    public static void main(String[] args) throws LoginException, InterruptedException {
        JDABuilder builder = JDABuilder.createDefault(TOKEN);

        builder.setActivity(Activity.playing("DestroyerOS v0.1"));

        instance = builder.addEventListeners(new ReadyListener()).build();

        instance.addEventListener(new MessageListener());

        instance.awaitReady();

        user = instance.getSelfUser();
    }

    public static JDA getInstance() { return instance; }

    public static User getBot() { return user; }
}
