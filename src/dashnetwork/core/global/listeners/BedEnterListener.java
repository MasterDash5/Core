package dashnetwork.core.global.listeners;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class BedEnterListener implements Listener {
    
    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        int sleeping = 0;

        for (Player online : world.getPlayers()) {
            if (online.isSleeping())
                sleeping++;
            else
                sleeping--;
        }

        if (sleeping > 0)
            world.setTime(0);
    }
    
}
