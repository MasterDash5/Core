package dashnetwork.core.skyblock;

import dashnetwork.core.Core;
import dashnetwork.core.skyblock.listeners.BlockFormListener;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;

public class Skyblock {

    public static World getWorld() {
        return Bukkit.getWorld("skyworld");
    }

    private Core plugin = Core.getInstance();

    public Skyblock() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new BlockFormListener(), plugin);
    }
}