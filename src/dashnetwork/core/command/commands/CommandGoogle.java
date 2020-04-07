package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import dashnetwork.core.utils.StringUtils;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class CommandGoogle extends CoreCommand {

    public CommandGoogle() {
        super("google", PermissionType.NONE, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (args.length > 0)
            MessageUtils.message(sender, "&6&l» &7Here you go lazy pants: &6https://www.google.com/#q=" + StringUtils.unsplit(args, "+"));
        else
            MessageUtils.usage(sender, label, "<question>");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return new ArrayList<>();
    }

}
