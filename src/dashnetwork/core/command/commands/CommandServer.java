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

        if (args.length > 1 && SenderUtils.isAdmin(sender)) {
            List<Player> selector = SelectorUtils.getPlayers(sender, args[0]);

            if (selector != null)
                targets = selector;
        } else if (player != null)
            targets.add(player);

        targets.removeIf(target -> !SenderUtils.canSee(sender, target));

        if (targets.isEmpty() || args.length <= 0)
            MessageUtils.message(sender, "&6&l» &7Servers: &6Creative, Hub, Prison, PvP, Skyblock, Skygrid");
        else {
            String name = args[0];

            if (name.equalsIgnoreCase("PvP"))
                name = "KitPvP";

            if (name.equalsIgnoreCase("Skyblock"))
                name = "skyworld";

            if (name.equalsIgnoreCase("Skygrid"))
                name = "skygrid-world";

            World world = Bukkit.getWorld(name);

            if (WorldUtils.isPlayerWorld(world) || SenderUtils.isStaff(sender)) {
                for (Player target : targets)
                    target.teleport(WorldUtils.getWarp(world), PlayerTeleportEvent.TeleportCause.PLUGIN);

                if (player == null || ListUtils.containsOtherThan(targets, player)) {
                    targets.remove(player);

                    String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(targets), false, false);
                    String names = ListUtils.fromList(NameUtils.toNames(targets), false, false);

                    MessageBuilder message = new MessageBuilder();
                    message.append("&6&l» ");
                    message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
                    message.append("&7 forced to " + world.getName());

                    MessageUtils.message(sender, message.build());
                }
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();

            for (World world : Bukkit.getWorlds()) {
                if (WorldUtils.isPlayerWorld(world) || SenderUtils.isAdmin(sender)) {
                    String name = world.getName();

                    if (name.equalsIgnoreCase("KitPvP"))
                        name = "PvP";

                    if (LazyUtils.anyEqualsIgnoreCase(name, "skyworld", "skyworld_nether"))
                        name = "Skyblock";

                    if (LazyUtils.anyEqualsIgnoreCase(name, "Survival_nether", "Survival_the_end"))
                        name = "Survival";

                    if (LazyUtils.anyEqualsIgnoreCase(name, "skygrid-world", "skygrid-world_nether", "skygrid-world_the_end"))
                        name = "Skygrid";

                    if (StringUtils.startsWithIgnoreCase(name, args[0]) && !completions.contains(name))
                        completions.add(name);
                }
            }

            return completions;
        }

        return null;
    }

}
