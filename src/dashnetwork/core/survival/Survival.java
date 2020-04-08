package dashnetwork.core.survival;

import dashnetwork.core.Core;
import dashnetwork.core.survival.listeners.TeleportListener;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;

public class Survival {

    public static World getWorld() {
        return Bukkit.getWorld("Survival");
    }

    public static World getNether() {
        return Bukkit.getWorld("Survival_nether");
    }

    public static World getEnd() {
        return Bukkit.getWorld("Survival_the_end");
    }

    private Core plugin = Core.getInstance();

    public Survival() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new TeleportListener(), plugin);
    }

}
