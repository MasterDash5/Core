package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import dashnetwork.core.utils.SenderUtils;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandClearchat extends CoreCommand {

    public CommandClearchat() {
        super("clearchat", PermissionType.STAFF);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        for (int i = 0; i < 100; i++)
            MessageUtils.broadcast(false, null, PermissionType.NONE, " ");

        MessageUtils.broadcast(true, null, PermissionType.NONE, "&6&l» &7Chat was cleared by &6" + SenderUtils.getDisplayName(sender));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }

}
