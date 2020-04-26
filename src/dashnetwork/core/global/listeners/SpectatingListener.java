package dashnetwork.core.global.listeners;

import com.destroystokyo.paper.event.player.PlayerStartSpectatingEntityEvent;
import dashnetwork.core.utils.User;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SpectatingListener implements Listener {

    @EventHandler
    public void onPlayerStartSpectatingEntity(PlayerStartSpectatingEntityEvent event) {
        Entity entity = event.getNewSpectatorTarget();

        if (entity instanceof Player) {
            Player target = (Player) entity;
            User user = User.getUser(target);

            if (user.isDash())
                event.setCancelled(true);
        }
    }

}
