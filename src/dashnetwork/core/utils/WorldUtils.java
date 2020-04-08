package dashnetwork.core.utils;

import dashnetwork.core.creative.Creative;
import dashnetwork.core.survival.Survival;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldUtils {

    private static String[] playerWorlds = { Creative.getWorld().getName(), "Hub", "Prison", "eskyworld", "skyworld_nether", Survival.getWorld().getName(), Survival.getNether().getName(), Survival.getEnd().getName(), "skygrid-world", "skygrid-world_nether", "skygrid-world_the_end", "KitPvP" };
    private static String[] staffWorlds = { "BuildTeamWorld", "WIP", "Creative_World", "DashRealm", "GoldenRealm", "AndreaRealm", "RedstoneReady" };

    public static boolean isPlayerWorld(World world) {
        return LazyUtils.anyEqualsIgnoreCase(world.getName(), playerWorlds);
    }

    public static boolean isStaffWorld(World world) {
        return LazyUtils.anyEqualsIgnoreCase(world.getName(), staffWorlds);
    }

}
