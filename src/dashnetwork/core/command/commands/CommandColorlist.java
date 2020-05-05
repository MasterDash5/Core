package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import org.bukkit.command.CommandSender;

public class CommandColorlist extends CoreCommand {

    public CommandColorlist() {
        super("colorlist", PermissionType.NONE, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        MessageUtils.message(sender, "&6&l» &11 &22 &33 &44 &55 &66 &77 &88 &99 &00 &aa &bb &cc &dd &ee &ff\n&6&l»&f &kk&f(k) &ll&f &mm&f &nn&f &oo");
    }

}
