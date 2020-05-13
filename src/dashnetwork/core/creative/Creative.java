package dashnetwork.core.creative;

import dashnetwork.core.Core;
import dashnetwork.core.creative.listeners.EntitySpawnListener;
import dashnetwork.core.creative.listeners.InteractListener;
import dashnetwork.core.creative.listeners.ProjectileListener;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;

public class Creative {

    private static World world = Bukkit.getWorld("Creative");
    private Core plugin = Core.getInstance();

    public static World getWorld() {
        return world;
    }

    public Creative() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new EntitySpawnListener(), plugin);
        manager.registerEvents(new InteractListener(), plugin);
        manager.registerEvents(new ProjectileListener(), plugin);
    }

}
