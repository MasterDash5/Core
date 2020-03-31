package dashnetwork.core.global;

import dashnetwork.core.Core;
import dashnetwork.core.global.listeners.*;
import dashnetwork.core.global.tasks.InventoryTask;
import dashnetwork.core.global.tasks.SpinTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class Global {

    private Core plugin = Core.getInstance();

    public Global() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new BlockListener(), plugin);
        manager.registerEvents(new ChatListener(), plugin);
        manager.registerEvents(new CommandListener(), plugin);
        manager.registerEvents(new InteractListener(), plugin);
        manager.registerEvents(new PortalListener(), plugin);
        manager.registerEvents(new ServerPingListener(), plugin);
        manager.registerEvents(new SignChangeListener(), plugin);
        manager.registerEvents(new WeatherListener(), plugin);

        // Register tasks
        new SpinTask().runTaskTimer(plugin, 0, 1);
        new InventoryTask().runTaskTimer(plugin, 0, 1);
    }

}
