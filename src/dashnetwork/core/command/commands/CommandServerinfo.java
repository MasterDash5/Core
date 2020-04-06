package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.PermissionType;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandServerinfo extends CoreCommand {

    public CommandServerinfo() {
        super("serverinfo", PermissionType.ADMIN, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }

}
