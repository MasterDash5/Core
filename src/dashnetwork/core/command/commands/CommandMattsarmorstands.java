package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import org.bukkit.command.CommandSender;

public class CommandMattsarmorstands extends CoreCommand {

    public CommandMattsarmorstands() {
        super("mattsarmorstands", PermissionType.NONE, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        MessageUtils.message(sender, "&c&lMattsArmorStands &6&l>> &6Developed by MM5. Version &cv1.0");
    }

}
