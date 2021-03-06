package dashnetwork.core.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.ListUtils;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

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
    public CommandNode onTabComplete(LiteralArgumentBuilder builder) {
        return builder.build();
    }

}
