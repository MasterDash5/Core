package dashnetwork.core.command.commands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.ListUtils;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import dashnetwork.core.utils.SenderUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.List;

public class CommandCrash extends CoreCommand {

    public CommandCrash() {
        super("crash", PermissionType.OWNER);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        Player target = null;

        if (args.length > 0) {
            target = Bukkit.getPlayer(args[0]);

            if (!SenderUtils.canSee(sender, target))
                target = null;
        }

        if (target == null)
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

            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(target, packet);
            } catch (Exception e) {
                e.printStackTrace();
            }

            final Player player = target;

            new BukkitRunnable() {
                public void run() {
                    player.kickPlayer(""); // Kick method needs to be ran synchronously
                }
            }.runTaskLater(plugin, 20);

            MessageUtils.message(sender, "&6&l» &7Crashed &6" + target.getName());
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1)
            return ListUtils.getOnlinePlayers(sender);
        return null;
    }

}
