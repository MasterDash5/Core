package dashnetwork.core.global.listeners;

import dashnetwork.core.utils.User;
import dashnetwork.core.utils.WorldUtils;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        World world = event.getBlock().getWorld();
        User user = User.getUser(player);

        if (!WorldUtils.isPlayerWorld(world) && !user.isAdmin())
            event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        World world = event.getBlock().getWorld();
        User user = User.getUser(player);

        if (!WorldUtils.isPlayerWorld(world) && !user.isAdmin())
            event.setCancelled(true);
    }

}
