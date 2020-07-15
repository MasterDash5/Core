package dashnetwork.core.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import org.bukkit.command.CommandSender;

public class CommandFuckoff extends CoreCommand {

    private static boolean fuckoff = false;

    public static boolean getFuckoff() {
        return fuckoff;
    }

    public CommandFuckoff() {
        super("fuckoff", PermissionType.OWNER, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        fuckoff = !fuckoff;

        if (fuckoff)
            MessageUtils.message(sender, "&6&l» &7New joins now disabled");
        else
            MessageUtils.message(sender, "&6&l» &7New joins now allowed again");
    }

    @Override
    public CommandNode onTabComplete(LiteralArgumentBuilder builder) {
        return builder.build();
    }

}
