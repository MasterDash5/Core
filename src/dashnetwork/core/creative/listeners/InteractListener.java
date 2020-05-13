package dashnetwork.core.creative.listeners;

import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.User;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);

        if (!user.isOwner() && user.isHolding("spawn_egg")) {
            event.setCancelled(true);
            event.setUseItemInHand(Event.Result.DENY);

            MessageUtils.message(player, "&6&l» &6Spawn eggs are currently disabled");
        }
    }

}
