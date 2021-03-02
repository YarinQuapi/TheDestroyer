package xyz.yarinlevi.thedestroyer.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.yarinlevi.thedestroyer.commands.DestroyCommand;
import xyz.yarinlevi.thedestroyer.utils.Command;

import java.util.HashMap;

public class DestroyerCommands extends ListenerAdapter {
    private final HashMap<String, Command> commandMap = new HashMap<>();

    public void loadCommands() {
        this.registerCommand(new DestroyCommand("destroy"));
    }

    @Nullable
    public Command getCommand(String key) {
        return commandMap.getOrDefault(key, null);
    }

    public void registerCommand(Command command) {
        commandMap.put(command.getName(), command);
    }

    public boolean unregisterCommand(String key) {
        return commandMap.containsKey(key) && commandMap.keySet().remove(key);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) {

            String[] rawCommandArgs = split(event.getMessage().getContentRaw());

            if (rawCommandArgs[0].startsWith("-")) {

                String rawCommand = "";
                for (char c : rawCommandArgs[0].toCharArray()) {
                    if (c != '-') {
                        rawCommand += c;
                    }
                }

                String[] commandArgs = new String[rawCommandArgs.length - 1];

                for (int i = 0; i < rawCommandArgs.length; i++) {
                    if (i != 0) {
                        commandArgs[i - 1] = rawCommandArgs[i];
                    }
                }

                Command command;
                if (commandMap.containsKey(rawCommand)) {
                    command = commandMap.get(rawCommand);

                    HashMap<String, Object> data = new HashMap<>();
                    data.put("guild", event.getGuild());
                    data.put("messageChannel", event.getMessage().getChannel());
                    data.put("message", event.getMessage());
                    data.put("user", event.getAuthor());


                    command.run(data, commandArgs);
                } else {
                    event.getMessage().getChannel().sendMessage("Command not found, please try again.").queue();
                }
            }
        }
    }


    public String[] split(String reason) {
        int i = 0;
        for (char c : reason.toCharArray())
            if (c == ' ')
                i++;
        String[] args = new String[i + 1];
        i = 0;
        String word = "";
        reason += " ";
        for (char c : reason.toCharArray())
        {
            if (c != ' ')
                word += c;
            else
            {
                args[i] = word;
                word = "";
                i++;
            }
        }
        if (i == 0)
            args[0] = word;
        return args;
    }
}
