package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandServer extends CoreCommand {

    public CommandServer() {
        super("server", PermissionType.NONE, false);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        List<Player> targets = new ArrayList<>();
        Player player = sender instanceof Player ? (Player) sender : null;

        if (args.length > 0 && SenderUtils.isOwner(sender)) {
            List<Player> selector = SelectorUtils.getPlayers(sender, args[0]);

            if (selector != null)
                targets.addAll(selector);
        } else if (player != null)
            targets.add(player);

        for (Player target : targets)
            if (!SenderUtils.canSee(sender, target))
                targets.remove(target);

        if (targets.isEmpty())
            MessageUtils.message(sender, "&6&l» &7Servers: &6Creative, Hub, Prison, PvP, Skyblock, Skygrid");
        else {
            String name = args[0];

            for (Player target : targets) {
                if (name.equalsIgnoreCase("skygrid"))
                    target.performCommand("skygrid");
                else {
                    World world = WorldUtils.getByAlias(name);

                    if (isWorldAllowed(sender, world))
                        target.teleport(world.getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                }
            }

            if (player == null || ListUtils.containsOtherThan(targets, player)) {
                targets.remove(player);

                String displaynames = ListUtils.fromList(ListUtils.toDisplayNames(targets), false, false);
                String names = ListUtils.fromList(ListUtils.toNames(targets), false, false);

                MessageBuilder message = new MessageBuilder();
                message.append("&6&l» ");
                message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
                message.append("&7 forced to " + name);

                MessageUtils.message(sender, message.build());
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();

            for (World world : Bukkit.getWorlds()) {
                String name = world.getName();

                if (isWorldAllowed(sender, world))
                    completions.add(name.replace("skyworld", "Skyblock").replace("KitPvP", "PvP"));

                completions.add("skygrid");
            }

            return completions;
        }
        return null;
    }

    private boolean isWorldAllowed(CommandSender sender, World world) {
        if (SenderUtils.isStaff(sender) && WorldUtils.isStaffWorld(world))
            return true;

        if (SenderUtils.isAdmin(sender))
            return true;

        return WorldUtils.isPlayerTeleportable(world);
    }

}
