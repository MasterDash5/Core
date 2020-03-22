package dashnetwork.core.utils;

import org.bukkit.command.CommandSender;

public enum PermissionType {

    OWNER,
    ADMIN,
    STAFF,
    OP,
    NONE;

    public boolean hasPermission(CommandSender sender) {
        switch (this) {
            case OWNER:
                return SenderUtils.isOwner(sender);
            case ADMIN:
                return SenderUtils.isAdmin(sender);
            case STAFF:
                return SenderUtils.isStaff(sender);
            case OP:
                return sender.isOp() || SenderUtils.isOwner(sender);
            default:
                return true;
        }
    }

}