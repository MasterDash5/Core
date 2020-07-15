package dashnetwork.core.utils;

import dashnetwork.core.creative.Creative;
import dashnetwork.core.survival.Survival;
import org.bukkit.Location;
import org.bukkit.World;

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
                return new Location(world, 601.5, 8, -347.5, -90, 0);
            case "skyworld":
                return new Location(world, 0.5, 150, 0.5, 180, 0);
            case "KitPvP":
                return new Location(world, 203.5, 240.5, 235.5, -90, 0);
            case "Creative":
                return new Location(world, 245.5, 75, 245.5, 0, 0);
        }

        return world.getSpawnLocation();
    }

    public static boolean canBuild(User user, World world) {
        if (user.isStaff() && isStaffWorld(world))
            return true;

        return user.isAdmin() || isPlayerWorld(world);
    }

}
