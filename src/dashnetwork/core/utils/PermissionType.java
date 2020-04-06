package dashnetwork.core.utils;

import org.bukkit.command.CommandSender;

public enum PermissionType {

    OWNER,
    ADMIN,
    STAFF,
    NONE;

    public boolean hasPermission(CommandSender sender) {
        switch (this) {
            case OWNER:
                return SenderUtils.isOwner(sender);
            case ADMIN:
                return SenderUtils.isAdmin(sender);
            case STAFF:
                return SenderUtils.isStaff(sender);
            default:
                return true;
        }
    }

    public String getPermission() {
        if (this.equals(NONE))
            return null;
        return "dashnetwork." + name().toLowerCase();
    }

}