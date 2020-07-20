package dashnetwork.core.command.commands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.minecraft.server.v1_16_R1.EntityPlayer;
import net.minecraft.server.v1_16_R1.PacketPlayOutGameStateChange;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
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

            PacketPlayOutGameStateChange packet = new PacketPlayOutGameStateChange(new PacketPlayOutGameStateChange.a(reason), value);

            for (Player target : targets)
                ((CraftPlayer) target).getHandle().playerConnection.sendPacket(packet);

            String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(targets), false ,false);
            String names = ListUtils.fromList(NameUtils.toNames(targets), false, false);

            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» &7Sent Gamestate packet to ");
            message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);

            MessageUtils.message(sender, message.build());
        }
    }

    @Override
    public CommandNode onTabComplete(LiteralArgumentBuilder builder) {
        return builder.then(Arguments.playersType("targets")
                .then(Arguments.integerType("state", 0, 11)
                        .then(Arguments.integerType("value")))).build();
    }

}
