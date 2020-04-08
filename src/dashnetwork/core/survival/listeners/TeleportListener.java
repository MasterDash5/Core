package dashnetwork.core.survival.listeners;

import dashnetwork.core.survival.Survival;
import dashnetwork.core.utils.DataUtils;
import dashnetwork.core.utils.LazyUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Map;

public class TeleportListener implements Listener {

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        World to = event.getTo().getWorld();

        if (LazyUtils.anyEquals(to, Survival.getWorld(), Survival.getNether(), Survival.getEnd())) {
            String uuid = player.getUniqueId().toString();
            Map<String, String> survivallocationList = DataUtils.getSurvivallocationList();

            if (survivallocationList.containsKey(uuid)) {
                String[] location = survivallocationList.get(uuid).split(":");
                World world = Bukkit.getWorld(location[0]);
                int x = Integer.valueOf(location[1]);
                int y = Integer.valueOf(location[2]);
                int z = Integer.valueOf(location[3]);
                int pitch = Integer.valueOf(location[4]);
                int yaw = Integer.valueOf(location[5]);

                event.setTo(new Location(world, x, y, z, pitch, yaw));
            }
        }
    }

}
