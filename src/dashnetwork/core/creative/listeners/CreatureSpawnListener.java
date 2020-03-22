package dashnetwork.core.creative.listeners;

import dashnetwork.core.creative.Creative;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawnListener implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getLocation().getWorld().equals(Creative.getWorld()))
            event.setCancelled(true);
    }

}
