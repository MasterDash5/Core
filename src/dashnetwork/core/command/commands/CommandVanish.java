package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandVanish extends CoreCommand {

    public CommandVanish() {
        super("vanish", PermissionType.STAFF, false);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        List<Player> targets = new ArrayList<>();
        Player player = sender instanceof Player ? (Player) sender : null;

        if (args.length > 0 && SenderUtils.isOwner(sender)) {
            List<Player> selector = SelectorUtils.getPlayers(sender, args[0]);

            if (selector != null)
                targets = selector;
        } else if (player != null)
            targets.add(player);

        targets.removeIf(target -> !SenderUtils.canSee(sender, target));

        if (targets.isEmpty())
            MessageUtils.usage(sender, label, "<player>");
        else {
            for (Player target : targets) {
                String displayname = target.getDisplayName();
                String name = target.getName();
                User user = User.getUser(target);
                boolean vanished = !user.isVanished();

                user.setVanished(vanished);

                if (vanished) {
                    for (Player online : Bukkit.getOnlinePlayers())
                        if (!SenderUtils.isStaff(online))
                            online.hidePlayer(plugin, target);

                    MessageBuilder message = new MessageBuilder();
                    message.append("&6&l» ");
                    message.append("&6" + displayname).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + name);
                    message.append("&7 has vanished. Poof.");

                    MessageUtils.broadcast(true, null, PermissionType.STAFF, message.build());
                    MessageUtils.broadcast(false, null, PermissionType.NONE, "&8[&4-&8]&c " + name);
                    MessageUtils.message(target, "&6&l» &7You are now hidden from non-staff members.");
                } else {
                    for (Player online : Bukkit.getOnlinePlayers())
                        online.showPlayer(plugin, target);

                    MessageBuilder message = new MessageBuilder();
                    message.append("&6&l» ");
                    message.append("&6" + displayname).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + name);
                    message.append("&7 is now visible.");

                    MessageUtils.broadcast(true, null, PermissionType.STAFF, message.build());
                    MessageUtils.broadcast(false, null, PermissionType.NONE, "&8[&a+&8]&2 " + name);
                    MessageUtils.message(target, "&6&l» &7You are now visible to non-staff members again.");
                }
            }

            if (player == null || ListUtils.containsOtherThan(targets, player)) {
                targets.remove(player);

                String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(targets), false, false);
                String names = ListUtils.fromList(NameUtils.toNames(targets), false, false);

                MessageBuilder message = new MessageBuilder();
                message.append("&6&l» ");
                message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
                message.append("&7 toggled Vanish");

                MessageUtils.message(sender, message.build());
            }
        }
    }

}
