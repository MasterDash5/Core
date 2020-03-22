package dashnetwork.core.utils;

import org.bukkit.Material;

public class MaterialUtils {

    public static boolean isAir(Material material) {
        return LazyUtils.anyEquals(material, Material.AIR, Material.CAVE_AIR, Material.VOID_AIR);
    }

}
