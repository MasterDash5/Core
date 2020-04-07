package dashnetwork.core.command.commands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandGamestate extends CoreCommand {

    public CommandGamestate() {
        super("gamestate", PermissionType.OWNER, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        List<Player> targets = new ArrayList<>();

        if (args.length > 2) {
            List<Player> selector = SelectorUtils.getPlayers(sender, args[0]);

            if (selector != null)
                targets = selector;
        }

        targets.removeIf(target -> !SenderUtils.canSee(sender, target));

        if (targets.isEmpty()) {
            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» &7Usage: &6/" + label + " <player> <state> <value>").clickEvent(ClickEvent.Action.OPEN_URL, "https://wiki.vg/Protocol#Change_Game_State");

            MessageUtils.message(sender, message.build());
        } else {
            int reason = Integer.parseInt(args[1]);
            float value = Float.parseFloat(args[2]);
            PacketContainer packet = new PacketContainer(PacketType.Play.Server.GAME_STATE_CHANGE);

            packet.getIntegers().write(0, reason);
            packet.getFloat().write(0, value);

            for (Player target : targets) {
                try {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(target, packet);
                } catch (Exception exception) {
                    MessageUtils.error(sender, exception);
                    exception.printStackTrace();
                }
            }

            String displaynames = ListUtils.fromList(ListUtils.toDisplayNames(targets), false ,false);
            String names = ListUtils.fromList(ListUtils.toNames(targets), false, false);

            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» &7Sent Gamestate packet to ");
            message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);

            MessageUtils.message(sender, message.build());
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1)
            return null;
        return new ArrayList<>();
    }

}
