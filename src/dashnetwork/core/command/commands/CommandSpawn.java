package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandSpawn extends CoreCommand {

    public CommandSpawn() {
        super("spawn", PermissionType.NONE, false);
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
                World world = target.getLocation().getWorld();
                String name = world.getName();
                target.teleport(WorldUtils.getWarp(world), PlayerTeleportEvent.TeleportCause.PLUGIN);

                MessageUtils.message(target, "&6&l» &7Teleporting to &6" + name);
            }

            if (player == null || ListUtils.containsOtherThan(targets, player)) {
                targets.remove(player);

                String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(targets), false, false);
                String names = ListUtils.fromList(NameUtils.toNames(targets), false, false);

                MessageBuilder message = new MessageBuilder();
                message.append("&6&l» ");
                message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
                message.append("&7 teleported to Spawn");

                MessageUtils.message(sender, message.build());
            }
        }
    }

}
