package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class CommandModlist extends CoreCommand {

    public CommandModlist() {
        super("modlist", PermissionType.STAFF, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        Player target = null;

        if (args.length > 0)
            target = SelectorUtils.getPlayer(sender, args[0]);

        if (target == null || !SenderUtils.canSee(sender, target)) {
            Map<String, List<UUID>> modlist = new HashMap<>();
            MessageBuilder message = new MessageBuilder();

            for (Player online : Bukkit.getOnlinePlayers()) {
                if (!SenderUtils.canSee(sender, online))
                    continue;

                User user = User.getUser(online);
                String client = user.getClient();
                List<UUID> players = modlist.getOrDefault(client, new ArrayList<>());

                players.add(online.getUniqueId());
                modlist.put(client, players);
            }

            for (Map.Entry<String, List<UUID>> entry : modlist.entrySet()) {
                List<String> displaynames = new ArrayList<>();
                List<String> names = new ArrayList<>();

                for (UUID uuid : entry.getValue()) {
                    target = Bukkit.getPlayer(uuid);
                    displaynames.add(target.getDisplayName());
                    names.add(target.getName());
                }


                if (!message.isEmpty())
                    message.append("\n");

                message.append("&6&l» &7[&6" + entry.getKey() + "&7] " + ListUtils.fromList(displaynames, false, false)).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + ListUtils.fromList(names, false, false));
            }

            if (message.isEmpty())
                MessageUtils.message(sender, "&6&l» &7Currently no online players");
            else
                MessageUtils.message(sender, message.build());
        } else
            MessageUtils.message(sender, "&6&l» &7Detected mods: &6" + ListUtils.fromList(User.getUser(target).getMods(), false, false));
    }

}
