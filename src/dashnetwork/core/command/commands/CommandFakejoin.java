package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.ListUtils;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandFakejoin extends CoreCommand {

    public CommandFakejoin() {
        super("fakejoin", PermissionType.ADMIN);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (args.length > 0)
            MessageUtils.broadcast(true, null, PermissionType.NONE, "&8[&a+&8] &2" + args[0]);
        else
            MessageUtils.usage(sender, label, "<name>");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 0)
            return ListUtils.getOnlinePlayers(sender);
        return null;
    }

}
