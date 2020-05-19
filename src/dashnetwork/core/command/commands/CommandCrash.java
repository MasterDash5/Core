package dashnetwork.core.command.commands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandCrash extends CoreCommand {

    public CommandCrash() {
        super("crash", PermissionType.OWNER, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        List<Player> targets = new ArrayList<>();

        if (args.length > 0) {
            List<Player> selector = SelectorUtils.getPlayers(sender, args[0]);

            if (selector != null)
                targets = selector;
        }

        targets.removeIf(target -> !SenderUtils.canSee(sender, target));

        if (targets.isEmpty())
            MessageUtils.usage(sender, label, "<player>");
        else {
            PacketContainer packet = new PacketContainer(PacketType.Play.Server.EXPLOSION);
            packet.getDoubles().write(0, Double.MAX_VALUE);
            packet.getDoubles().write(1, Double.MAX_VALUE);
            packet.getDoubles().write(2, Double.MAX_VALUE);
            packet.getFloat().write(0, Float.MAX_VALUE);
            packet.getFloat().write(1, Float.MAX_VALUE);
            packet.getFloat().write(2, Float.MAX_VALUE);
            packet.getFloat().write(3, Float.MAX_VALUE);
            packet.getPositionCollectionModifier().write(0, Collections.EMPTY_LIST);

            for (Player target : targets) {
                try {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(target, packet);
                } catch (Exception exception) {
                    MessageUtils.error(sender, exception);
                    exception.printStackTrace();
                }

                final Player player = target;

                new BukkitRunnable() {
                    public void run() {
                        player.kickPlayer(""); // Kick method needs to be ran synchronously (plus delay helps)
                    }
                }.runTaskLater(plugin, 20);
            }

            String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(targets), false ,false);
            String names = ListUtils.fromList(NameUtils.toNames(targets), false, false);

            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» &7Crashed ");
            message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);

            MessageUtils.message(sender, message.build());
        }
    }

    @Override
    public CommandNode onTabComplete(LiteralArgumentBuilder builder) {
        return builder.then(Arguments.playersType("targets")).build();
    }

}
