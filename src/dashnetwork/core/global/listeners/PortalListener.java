package dashnetwork.core.global.listeners;

import dashnetwork.core.survival.Survival;
import dashnetwork.core.utils.LazyUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;

public class PortalListener implements Listener {

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        String from = event.getFrom().getWorld().getName();
        String to = event.getTo().getWorld().getName();
        String[] worlds = { Survival.getWorld().getName(), Survival.getNether().getName(), Survival.getEnd().getName(), "skyworld", "skyworld_nether", "skygrid-world", "skygrid-world_nether", "skygrid-world_the_end" };

        if (!LazyUtils.anyEquals(from, worlds) || !LazyUtils.anyEquals(to, worlds))
            event.setCancelled(true);
    }

    @EventHandler
    public void onEntityPortal(EntityPortalEvent event) {
        event.setCancelled(true);
    }

}
