package dashnetwork.core.command.commands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import dashnetwork.core.utils.SenderUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandGamestate extends CoreCommand {

    public CommandGamestate() {
        super("gamestate", PermissionType.OWNER);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        Player target = null;

        if (args.length > 2)
            target = Bukkit.getPlayer(args[0]);

        if (target == null || !SenderUtils.canSee(sender, target))
            MessageUtils.usage(sender, label, "<player> <state> <value>");
        else {
            int reason = Integer.parseInt(args[1]);
            float value = Float.parseFloat(args[2]);
            PacketContainer packet = new PacketContainer(PacketType.Play.Server.GAME_STATE_CHANGE);

            packet.getIntegers().write(0, reason);
            packet.getFloat().write(0, value);

            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(target, packet);
            } catch (Exception exception) {
                MessageUtils.error(sender, exception);
                exception.printStackTrace();
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }

}
