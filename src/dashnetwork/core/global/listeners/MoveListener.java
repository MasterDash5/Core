package dashnetwork.core.global.listeners;

import dashnetwork.core.utils.User;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class MoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        Entity nametag = user.getNametag();

        if (user.isLocked())
            event.setCancelled(true);

        if (nametag != null)
            nametag.teleport(player.getLocation().add(0, 1.8, 0));
    }

}
