package xyz.yarinlevi.thedestroyer.helpers;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

public class PermissionHandler {
    public static boolean hasPermission(Member member, Permission permission) {
        return member.hasPermission(permission);
    }
}
