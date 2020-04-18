package dashnetwork.core.global.listeners;

import dashnetwork.core.utils.User;
import dashnetwork.core.utils.WorldUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();

        if (damager instanceof Player) {
            Player player = (Player) damager;
            User user = User.getUser(player);

            if (!WorldUtils.canBuild(user, player.getWorld()))
                event.setCancelled(true);

            if (user.isLocked())
                event.setCancelled(true);
        }
    }

}
