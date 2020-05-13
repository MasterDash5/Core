package dashnetwork.core.creative.listeners;

import dashnetwork.core.creative.Creative;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

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
        EntityType type = event.getEntityType();

        if (!type.equals(EntityType.PLAYER)) {
            new Thread(() -> {
                List<Entity> entities = new ArrayList<>();

                for (Entity entity : event.getEntity().getLocation().getChunk().getEntities())
                    if (entity.getType().equals(type))
                        entities.add(entity);

                while (entities.size() > 100) {
                    Entity entity = entities.get(0);
                    entity.remove();
                    entities.remove(0);
                }
            }).start();
        }
    }

}
