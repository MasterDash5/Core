package dashnetwork.core.global.listeners;

import dashnetwork.core.utils.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);

        if (user.isLocked())
            event.setCancelled(true);
    }

}
