package dashnetwork.core.creative;

import dashnetwork.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class Creative {

    private static World world = Bukkit.getWorld("Creative");
    private Core plugin = Core.getInstance();

    public static World getWorld() {
        return world;
    }

    public Creative() {}

}
