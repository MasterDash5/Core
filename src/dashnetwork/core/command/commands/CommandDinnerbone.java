package dashnetwork.core.command.commands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import net.minecraft.server.v1_16_R1.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CommandDinnerbone extends CoreCommand {

    public CommandDinnerbone() {
        super("dinnerbone", PermissionType.OWNER, false);
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
                User user = User.getUser(target);
                boolean dinnerbone = !user.isDinnerbone();

                if (dinnerbone) {
                    Location location = target.getLocation().add(0, 1.8, 0);
                    ArmorStand nametag = location.getWorld().spawn(location, ArmorStand.class);
                    nametag.setGravity(false);
                    nametag.setVisible(false);
                    nametag.setMarker(true);
                    nametag.setSmall(true);
                    nametag.setBasePlate(false);
                    nametag.setCustomName(target.getName());
                    nametag.setCustomNameVisible(true);
                    nametag.setMetadata("canMoveWorlds", new FixedMetadataValue(plugin, true));

                    user.setNametag(nametag);

                    PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
                    packet.getIntegerArrays().write(0, new int[] { nametag.getEntityId() });

                    user.sendPacket(packet);

                    MessageUtils.message(target, "&6&l» &7You are now Dinnerbone");
                } else {
                    user.getNametag().remove();
                    user.setNametag(null);

                    MessageUtils.message(target, "&6&l» &7You are no longer Dinnerbone");
                }

                for (Player online : Bukkit.getOnlinePlayers())
                    online.hidePlayer(plugin, target);

                user.setDinnerbone(dinnerbone);

                for (Player online : Bukkit.getOnlinePlayers())
                    online.showPlayer(plugin, target);
            }

            if (player == null || ListUtils.containsOtherThan(targets, player)) {
                targets.remove(player);

                String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(targets), false, false);
                String names = ListUtils.fromList(NameUtils.toNames(targets), false, false);

                MessageBuilder message = new MessageBuilder();
                message.append("&6&l» ");
                message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
                message.append("&7 toggled Dinnerbone");

                MessageUtils.message(sender, message.build());
            }
        }
    }

    @Override
    public CommandNode onTabComplete(LiteralArgumentBuilder builder) {
        return builder.then(Arguments.playersType("targets")).build();
    }

}
