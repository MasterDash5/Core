package dashnetwork.core.global.listeners;

import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.User;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener {

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        String world = event.getTo().getWorld().getName();

        player.releaseLeftShoulderEntity();
        player.releaseRightShoulderEntity();

        if (!user.isAdmin() && world.equals("Prison")) {
            event.setCancelled(true);

            MessageUtils.message(player, "&6&l» &6Prison is currently under maintenance");
        }
    }

    @EventHandler
    public void onEntityTeleport(EntityTeleportEvent event) {
        World to = event.getTo().getWorld();
        World from = event.getFrom().getWorld();

        if (!to.equals(from))
            event.setCancelled(true);
    }

}
