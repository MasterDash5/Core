package dashnetwork.core.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.Arrays;
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
    public CommandNode onTabComplete(LiteralArgumentBuilder builder) {
        List<String> servers = Arrays.asList("Creative", "Hub", "Prison", "Skyblock", "Skygrid", "PvP");

        for (String server : servers)
            builder.then(Arguments.literal(server));

        return builder.build();
    }

}
