package xyz.yarinlevi.thedestroyer.utils;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import xyz.yarinlevi.thedestroyer.Main;
import xyz.yarinlevi.thedestroyer.TheDestroyer;

import java.util.List;

public class MemberUtils {
    public static int countInteractableMembers(Guild guild) {
        List<Member> memberListLoaded = guild.loadMembers().get();

        int nonRanked = 0;
        Member self = guild.getMember(Main.instance.getUser());
        assert self != null;
        for (Member mem : memberListLoaded) {
            if(self.canInteract(mem)) {
                nonRanked = nonRanked + 1;
            }
        }

        return nonRanked;
    }

    public static void quickBan(Member member, String reason) {
        member.ban(30, reason).queue();
    }
}
