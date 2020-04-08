package dashnetwork.core.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import dashnetwork.core.Core;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class WorldGuardUtils {

    private static Core plugin = Core.getInstance();

    private static boolean hasWorldGuard() {
        Plugin worldGuard = plugin.getServer().getPluginManager().getPlugin("WorldGuard");

        return worldGuard != null && worldGuard instanceof WorldGuardPlugin;
    }

    public static List<String> getRegions(Location location) {
        List<String> regions = new ArrayList<>();

        if (hasWorldGuard()) {
            RegionManager manager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(location.getWorld()));

            for (ProtectedRegion region : manager.getRegions().values())
                if (region.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ()))
                    regions.add(region.getId());
        }

        return regions;
    }

}
