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
        super("oplist", PermissionType.OP);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        Map<String, String> operators = new HashMap<>();
        MessageBuilder message = new MessageBuilder();

        for (OfflinePlayer operator : Bukkit.getOperators())
            operators.put(operator.getName(), operator.isOnline() ? "&aOnline" : "&cOffline &7for &6" + TimeUtils.millisecondsToWords(operator.getLastSeen()));

        message.append("&6&l» &6Operators:");

        for (Map.Entry<String, String> entry : operators.entrySet()) {
            message.append(" ");
            message.append(entry.getKey()).hoverEvent(HoverEvent.Action.SHOW_TEXT, entry.getValue());
        }

        if (operators.isEmpty())
            message.append("&7 None");

        MessageUtils.message(sender, message.build());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }

}
