package dashnetwork.core.global.listeners;

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
        Object[] worlds = { "Survival", "Survival_nether", "Survival_the_end", "skyworld", "skyworld_nether" };

        if (!LazyUtils.anyEquals(from, worlds) || !LazyUtils.anyEquals(to, worlds))
            event.setCancelled(true);
    }

    @EventHandler
    public void onEntityPortal(EntityPortalEvent event) {
        event.setCancelled(true);
    }

}
