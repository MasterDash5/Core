package dashnetwork.core.command;

import dashnetwork.core.Core;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.SenderUtils;
import org.bukkit.command.*;

import java.util.ArrayList;
import java.util.List;

public abstract class CoreCommand implements CommandExecutor, TabCompleter {

    private static List<CoreCommand> commands = new ArrayList<>();
    protected static Core plugin = Core.getInstance();
    private PermissionType permission;

    public CoreCommand(String label, PermissionType permission) {
        PluginCommand command = plugin.getServer().getPluginCommand(label);
        command.setExecutor(this);
        command.setTabCompleter(this);

        this.permission = permission;

        commands.add(this);
    }

    public static List<CoreCommand> getCommands() {
        return commands;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (permission.hasPermission(sender))
            onCommand(sender, label, args);
        else
            MessageUtils.noPermissions(sender);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (permission.hasPermission(sender))
            return onTabComplete(sender, label, args);
        return null;
    }

    protected abstract void onCommand(CommandSender sender, String label, String[] args);
    protected abstract List<String> onTabComplete(CommandSender sender, String label, String[] args);

    protected enum PermissionType {
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
                    return sender.isOp();
                default:
                    return true;
            }
        }
    }

}
