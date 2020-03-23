package dashnetwork.core.global;

import dashnetwork.core.Core;
import dashnetwork.core.global.listeners.CommandListener;
import dashnetwork.core.global.listeners.PortalListener;
import dashnetwork.core.global.listeners.ServerPingListener;
import dashnetwork.core.global.listeners.WeatherListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class Global {

    private Core plugin = Core.getInstance();

    public Global() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new CommandListener(), plugin);
        manager.registerEvents(new PortalListener(), plugin);
        manager.registerEvents(new ServerPingListener(), plugin);
        manager.registerEvents(new WeatherListener(), plugin);
    }

}
