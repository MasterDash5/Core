package dashnetwork.core.global.listeners;

import dashnetwork.core.utils.VersionUtils;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerPingListener implements Listener {

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        String motd = "&6&l» &6DashNetwork &7" + VersionUtils.getOldest() + "-" + VersionUtils.getLatest();

        event.setMotd(ChatColor.translateAlternateColorCodes('&', motd)); // TODO: Think of something to put here
    }

}
