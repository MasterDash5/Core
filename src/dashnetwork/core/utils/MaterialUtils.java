package dashnetwork.core.utils;

import org.bukkit.Material;

public class MaterialUtils {

    public static boolean isAir(Material material) {
        return LazyUtils.anyEquals(material, Material.AIR, Material.CAVE_AIR, Material.VOID_AIR);
    }

    public static boolean isSword(Material material) {
        return LazyUtils.anyEquals(material, Material.WOODEN_SWORD, Material.STONE_SWORD, Material.GOLDEN_SWORD, Material.IRON_SWORD, Material.DIAMOND_SWORD);
    }

}
