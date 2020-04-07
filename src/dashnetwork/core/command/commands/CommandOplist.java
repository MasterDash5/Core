package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandOplist extends CoreCommand {

    public CommandOplist() {
        super("oplist", PermissionType.ADMIN, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        List<String> operators = new ArrayList<>();

        for (OfflinePlayer operator : Bukkit.getOperators())
            operators.add(operator.getName());

        MessageUtils.message(sender, "&6&l» &7Operators: &6" + ListUtils.fromList(operators, false, false));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return new ArrayList<>();
    }

}
