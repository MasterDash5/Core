package dashnetwork.core.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dashnetwork.core.Core;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import me.lucko.commodore.Commodore;
import me.lucko.commodore.CommodoreProvider;
import org.bukkit.command.*;

public abstract class CoreCommand implements CommandExecutor {

    protected static Core plugin = Core.getInstance();
    private PermissionType permission;
    private boolean async;

    public CoreCommand(String label, PermissionType permission, boolean async) {
        PluginCommand command = plugin.getCommand(label);
        command.setExecutor(this);
        command.setPermission(permission.getPermission());

        Commodore commodore = CommodoreProvider.getCommodore(plugin);
        commodore.register(command, (LiteralCommandNode<?>) onTabComplete(LiteralArgumentBuilder.literal(label)), permission::hasPermission);

        for (String alias : command.getAliases())
            commodore.register(command, (LiteralCommandNode<?>) onTabComplete(LiteralArgumentBuilder.literal(alias)), permission::hasPermission);

        this.permission = permission;
        this.async = async;
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

    public abstract void onCommand(CommandSender sender, String label, String[] args);

    public abstract CommandNode onTabComplete(LiteralArgumentBuilder builder);

}
