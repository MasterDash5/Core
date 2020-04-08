package dashnetwork.core.global.listeners;

import dashnetwork.core.utils.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class KickListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerKick(PlayerKickEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);

        if (user.isOwner())
            event.setCancelled(true);
    }

}
