package xyz.yarinlevi.thedestroyer.listeners;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import xyz.yarinlevi.thedestroyer.Main;
import xyz.yarinlevi.thedestroyer.helpers.PermissionHandler;

import static java.lang.Thread.sleep;

public class MessageListener extends ListenerAdapter {
    private static final String PREFIX = "-";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().startsWith(PREFIX)) {
            User author = event.getAuthor();
            Message msg = event.getMessage();
            MessageChannel channel = msg.getChannel();

            if(msg.getContentRaw().startsWith(PREFIX + "baneveryone")) {
                assert msg.getMember() != null;
                if(!author.isBot() && PermissionHandler.hasPermission(msg.getMember(), Permission.ADMINISTRATOR)) {
                    Guild guild = msg.getGuild();

                    int nonRanked = countInteractable(guild);
                    int totalMembers = guild.getMemberCount();

                    channel.sendMessage(String.format("Detected %s bannable users. Executing..", nonRanked)).queue();

                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    int banId = 0;
                    Member self = guild.getMember(Main.getBot());
                    assert self != null;
                    for (Member mem : guild.getMembers()) {
                        if(self.canInteract(mem)) {
                            banId++;
                            channel.sendMessage("Banning #" + banId + ".. (" + mem.getEffectiveName() + ")").queue();

                            try {
                                sleep(125);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            mem.ban(0, "The Ban Hammer Has Spoken!").queue();
                        }
                    }

                    channel.sendMessage(String.format("Successfully banned %s out of %s bannable, out of %s total members", banId, nonRanked, totalMembers)).queue();
                }
            }
        }
    }

    public int countInteractable(Guild guild) {
        int nonRanked = 0;
        Member self = guild.getMember(Main.getBot());
        assert self != null;
        for (Member mem : guild.getMembers()) {
            if(self.canInteract(mem)) {
                nonRanked = nonRanked + 1;
            }
        }

        return nonRanked;
    }
}
