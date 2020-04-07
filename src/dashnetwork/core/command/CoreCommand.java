package dashnetwork.core.command;

import dashnetwork.core.Core;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import org.bukkit.command.*;

import java.util.ArrayList;
import java.util.List;

public abstract class CoreCommand implements CommandExecutor, TabCompleter {

    private static List<CoreCommand> commands = new ArrayList<>();
    protected static Core plugin = Core.getInstance();
    private PermissionType permission;
    private boolean async;

    public CoreCommand(String label, PermissionType permission, boolean async) {
        PluginCommand command = plugin.getCommand(label);
        command.setExecutor(this);
        command.setTabCompleter(this);
        command.setPermission(permission.getPermission());

        this.permission = permission;
        this.async = async;

        commands.add(this);
    }

    public static List<CoreCommand> getCommands() {
        return commands;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (permission.hasPermission(sender)) {
            if (async)
                new Thread(() -> { onCommand(sender, label, args); }).start();
            else
                onCommand(sender, label, args);
        } else
            MessageUtils.noPermissions(sender);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (permission.hasPermission(sender))
            return onTabComplete(sender, label, args);
        return new ArrayList<>();
    }

    public abstract void onCommand(CommandSender sender, String label, String[] args);
    public abstract List<String> onTabComplete(CommandSender sender, String label, String[] args);

}
