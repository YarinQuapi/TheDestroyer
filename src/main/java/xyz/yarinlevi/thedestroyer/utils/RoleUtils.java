package xyz.yarinlevi.thedestroyer.utils;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class RoleUtils {
    public static int countInteractableRoles(Guild guild) {
        int interactable = 0;

        for (Role role : guild.getRoles()) {
            if (guild.getSelfMember().canInteract(role)) {
                interactable++;
            }
        }

        return interactable;
    }
}
