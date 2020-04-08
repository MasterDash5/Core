package dashnetwork.core.global.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

public class ServerCommandListener implements Listener {

    @EventHandler
    public void onServerCommand(ServerCommandEvent event) {
        if (event.getSender().equals(Bukkit.getConsoleSender()) && event.getCommand().equalsIgnoreCase("list"))
            event.setCancelled(true); // MultiCraft likes to spam this command
    }

}
