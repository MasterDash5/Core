package dashnetwork.core.global.listeners;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        World from = player.getWorld();
        World to = event.getRespawnLocation().getWorld();

        if (!to.equals(from))
            event.setRespawnLocation(from.getSpawnLocation());
    }

}
