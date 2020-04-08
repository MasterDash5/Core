package dashnetwork.core.global.listeners;

import dashnetwork.core.utils.User;
import dashnetwork.core.utils.WorldUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropItemListener implements Listener {

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);

        if (!user.isStaff() && WorldUtils.isStaffWorld(player.getWorld()))
            event.setCancelled(true);
    }

}
