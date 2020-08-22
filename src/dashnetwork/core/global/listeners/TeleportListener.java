package dashnetwork.core.global.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.User;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener {

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        String world = event.getTo().getWorld().getName();
        Entity nametag = user.getNametag();

        player.releaseLeftShoulderEntity();
        player.releaseRightShoulderEntity();

        if (!user.isAdmin() && world.equals("Prison")) {
            event.setCancelled(true);

            MessageUtils.message(player, "&6&l» &6Prison is currently under maintenance");
        }

        if (nametag != null) {
            nametag.teleport(event.getTo().clone().add(0, 1.8, 0));

            PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
            packet.getIntegerArrays().write(0, new int[] { nametag.getEntityId() });

            user.sendPacket(packet);
        }
    }

    @EventHandler
    public void onEntityTeleport(EntityTeleportEvent event) {
        World to = event.getTo().getWorld();
        World from = event.getFrom().getWorld();

        if (!to.equals(from) && !event.getEntity().hasMetadata("canMoveWorlds"))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);

        if (user.isDinnerbone()) {
            PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
            packet.getIntegerArrays().write(0, new int[] { user.getNametag().getEntityId() });

            user.sendPacket(packet);
        }
    }

}
