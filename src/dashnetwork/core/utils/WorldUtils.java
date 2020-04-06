package dashnetwork.core.utils;

import org.bukkit.World;

public class WorldUtils {

    public static boolean isPlayerWorld(World world) {
        return LazyUtils.anyEquals(world.getName(), "Creative", "Hub", "skyworld", "skyworld_nether" , "Survival", "Survival_nether", "Survival_the_end", "KitPvP");
    }

    public static boolean isPlayerTeleportable(World world) {
        return isPlayerWorld(world) && !LazyUtils.anyEndsWith(world.getName(), "_nether", "_the_end");
    }

    public static boolean isStaffWorld(World world) {
        return LazyUtils.anyEquals(world.getName(), "BuildTeamWorld", "WIP");
    }

}
