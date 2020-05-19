package dashnetwork.core.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.Arguments;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import dashnetwork.core.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class CommandConsole extends CoreCommand {

    public CommandConsole() {
        super("console", PermissionType.OWNER, false);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (args.length > 0)
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), StringUtils.unsplit(args, " "));
        else
            MessageUtils.message(sender, "&6&l» &6Usage: &7/console <command>");
    }

    @Override
    public CommandNode onTabComplete(LiteralArgumentBuilder builder) {
        return builder.then(Arguments.stringTypeGreedy("command")).build();
    }

}
