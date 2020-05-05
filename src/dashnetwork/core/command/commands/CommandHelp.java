package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.PermissionType;
import org.bukkit.command.CommandSender;

public class CommandHelp extends CoreCommand {

    public CommandHelp() {
        super("help", PermissionType.NONE, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {

    }

}
