package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.ListUtils;
import dashnetwork.core.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class CommandOplist extends CoreCommand {

    public CommandOplist() {
        super("oplist", PermissionType.OP);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        List<String> names = new ArrayList<>();

        for (OfflinePlayer operator : Bukkit.getOperators())
            names.add(operator.getName());

        MessageUtils.message(sender, "&6&l» &6Operators: &7" + ListUtils.fromList(names, false, false));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }

}
