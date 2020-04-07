package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class CommandList extends CoreCommand {

    public CommandList() {
        super("list", PermissionType.NONE, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        Map<String, List<UUID>> onlinelist = new HashMap<>();
        MessageBuilder message = new MessageBuilder();

        for (World world : Bukkit.getWorlds()) {
            for (Player online : world.getPlayers()) {
                if (!SenderUtils.canSee(sender, online))
                    continue;

                String worldName = world.getName().replace("skyworld", "Skyblock");
                List<UUID> players = onlinelist.getOrDefault(worldName, new ArrayList<>());

                players.add(online.getUniqueId());
                onlinelist.put(worldName, players);
            }
        }

        for (Map.Entry<String, List<UUID>> entry : onlinelist.entrySet()) {
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
        return new ArrayList<>();
    }

}
