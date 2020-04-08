package dashnetwork.core.global.listeners;

import dashnetwork.core.utils.User;
import dashnetwork.core.utils.WorldUtils;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        User user = User.getUser(player);

        if (!user.isAdmin() && WorldUtils.isPlayerWorld(world))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        User user = User.getUser(player);

        if (!user.isAdmin() && WorldUtils.isPlayerWorld(world))
            event.setCancelled(true);
    }

}
