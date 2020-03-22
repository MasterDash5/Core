package dashnetwork.core.creative;

import dashnetwork.core.Core;
import dashnetwork.core.creative.listeners.CreatureSpawnListener;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;

public class Creative {

    private static World world = Bukkit.getWorld("Creative"); // Future proofing
    private Core plugin = Core.getInstance();

    public static World getWorld() {
        return world;
    }

    public Creative() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new CreatureSpawnListener(), plugin);
    }

}
