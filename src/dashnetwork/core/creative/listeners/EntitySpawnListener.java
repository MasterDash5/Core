package dashnetwork.core.creative.listeners;

import dashnetwork.core.creative.Creative;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawnListener implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (Creative.getWorld().equals(event.getLocation().getWorld()))
            event.setCancelled(true);
    }

}
