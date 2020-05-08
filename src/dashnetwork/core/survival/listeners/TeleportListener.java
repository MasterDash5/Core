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
        Location from = event.getFrom();
        World fromWorld = from.getWorld();
        World toWorld = event.getTo().getWorld();
        World[] survivalWorlds = { Survival.getWorld(), Survival.getNether(), Survival.getEnd() };

        if (LazyUtils.anyEquals(toWorld, survivalWorlds) || LazyUtils.anyEquals(fromWorld, survivalWorlds)) {
            Map<String, String> survivalLocationList = DataUtils.getSurvivalLocationList();

            if (LazyUtils.anyEquals(toWorld, survivalWorlds) && !LazyUtils.anyEquals(fromWorld, survivalWorlds)) {
                String uuid = player.getUniqueId().toString();

                if (survivalLocationList.containsKey(uuid)) {
                    String[] location = survivalLocationList.get(uuid).split(":");
                    World world = Bukkit.getWorld(location[0]);
                    int x = Integer.valueOf(location[1]);
                    int y = Integer.valueOf(location[2]);
                    int z = Integer.valueOf(location[3]);
                    int yaw = Integer.valueOf(location[4]);
                    int pitch = Integer.valueOf(location[5]);

                    event.setTo(new Location(world, x, y, z, yaw, pitch));
                }
            }

            if (LazyUtils.anyEquals(fromWorld, Survival.getWorld(), Survival.getNether(), Survival.getEnd())) {
                String uuid = player.getUniqueId().toString();

                String world = fromWorld.getName();
                int x = from.getBlockX();
                int y = from.getBlockY();
                int z = from.getBlockZ();
                int yaw = (int) from.getYaw();
                int pitch = (int) from.getPitch();
                String location = world + ":" + x + ":" + y + ":" + z + ":" + yaw + ":" + pitch;

                survivalLocationList.put(uuid, location);
            }
        }
    }

}
