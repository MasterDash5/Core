package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class CommandVersionlist extends CoreCommand {

    public CommandVersionlist() {
        super("versionlist", PermissionType.NONE);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        Map<String, List<UUID>> versionlist = new HashMap<>();
        MessageBuilder message = new MessageBuilder();

        for (Player online : Bukkit.getOnlinePlayers()) {
            if (!SenderUtils.canSee(sender, online))
                continue;

            String version = VersionUtils.getPlayerVersion(online);
            List<UUID> players = versionlist.getOrDefault(version, new ArrayList<>());

            players.add(online.getUniqueId());
            versionlist.put(version, players);
        }

        for (Map.Entry<String, List<UUID>> entry : versionlist.entrySet()) {
            List<String> displaynames = new ArrayList<>();
            List<String> names = new ArrayList<>();

            for (UUID uuid : entry.getValue()) {
                Player target = Bukkit.getPlayer(uuid);
                displaynames.add(target.getDisplayName());
                names.add(target.getName());
            }

            message.append("&6&l» &7[&6" + entry.getKey() + "&7] " + ListUtils.fromList(displaynames, false, true))
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + ListUtils.fromList(names, false, true));
        }

        if (message.isEmpty())
            MessageUtils.message(sender, "&6&l» &7Currently no online players");
        else
            MessageUtils.message(sender, message.build());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }

}
