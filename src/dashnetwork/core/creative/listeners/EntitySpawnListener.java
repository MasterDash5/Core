package dashnetwork.core.creative.listeners;

import dashnetwork.core.Core;
import dashnetwork.core.creative.Creative;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class EntitySpawnListener implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getEntity().getWorld().equals(Creative.getWorld()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        EntityType type = entity.getType();

        if (entity.getWorld().equals(Creative.getWorld()) && !type.equals(EntityType.PLAYER)) {
            new BukkitRunnable() {
                public void run() {
                    List<Entity> entities = new ArrayList<>();

                    for (Entity entity : entity.getLocation().getChunk().getEntities())
                        if (entity.getType().equals(type))
                            entities.add(entity);

                    while (entities.size() > 100) {
                        Entity entity = entities.get(0);
                        entity.remove();
                        entities.remove(0);
                    }
                }
            }.runTaskAsynchronously(Core.getInstance());
        }
    }

}
