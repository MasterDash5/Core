package dashnetwork.core.global.listeners;

import dashnetwork.core.utils.User;
import dashnetwork.core.utils.WorldUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class PickupItemListener implements Listener {

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;
            User user = User.getUser(player);

            if (!user.isStaff() && WorldUtils.isStaffWorld(player.getWorld()))
                event.setCancelled(true);
        }
    }

}
