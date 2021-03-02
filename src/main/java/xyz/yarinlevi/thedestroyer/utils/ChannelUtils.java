package xyz.yarinlevi.thedestroyer.utils;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import xyz.yarinlevi.thedestroyer.Main;

public class ChannelUtils {
    public static int countInteractableChannels(Guild guild) {
        int interactable = 0;

        for (TextChannel textChannel : guild.getTextChannels()) {
            interactable++;
        }

        for (VoiceChannel voiceChannel : guild.getVoiceChannels()) {
            interactable++;
        }

        return interactable;
    }
}
