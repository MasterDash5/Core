package dashnetwork.core.utils;

import dashnetwork.core.creative.Creative;
import dashnetwork.core.survival.Survival;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class WorldUtils {

    private static String[] playerWorlds = { Creative.getWorld().getName(), "Hub", "Prison", "skyworld", "skyworld_nether", Survival.getWorld().getName(), Survival.getNether().getName(), Survival.getEnd().getName(), "skygrid-world", "skygrid-world_nether", "skygrid-world_the_end", "KitPvP" };
    private static String[] staffWorlds = { "BuildTeamWorld", "WIP", "Creative_World", "DashRealm", "GoldenRealm", "AndreaRealm", "RedstoneReady" };

    public static boolean isPlayerWorld(World world) {
        return LazyUtils.anyEqualsIgnoreCase(world.getName(), playerWorlds);
}

    public static boolean isStaffWorld(World world) {
        return LazyUtils.anyEqualsIgnoreCase(world.getName(), staffWorlds);
    }

    public static Location getWarp(World world) {
        String name = world.getName();

        switch (name) {
            case "Hub":
                return new Location(world, 601.5, 8.0, -347.5, -90, 0);
            case "skyworld":
                return new Location(world, 0.5, 150, 0.5, 180, 0);
            case "KitPvP":
                return new Location(world, 203.5, 240.5, 235.5, -90, 0);
        }

        return world.getSpawnLocation();
    }

}
