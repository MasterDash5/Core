package dashnetwork.core.global.listeners;

import dashnetwork.core.Core;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerPingListener implements Listener {

    private List<String> recentPings = new ArrayList<>();

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        String address = event.getAddress().getHostAddress();
        String motd = "&6&l» &6DashNetwork &7" + VersionUtils.getOldest() + "-" + VersionUtils.getLatest(); // TODO: Think of something better to put here

        event.setMotd(ChatColor.translateAlternateColorCodes('&', motd));

        if (recentPings.contains(address)) {
            new Thread(() -> {
                Map<String, List<String>> addresses = DataUtils.getOfflineList();

                if (addresses.containsKey(address)) {
                    List<String> names = addresses.get(address);
                    MessageBuilder message = new MessageBuilder();

                    message.append("&c&lPS &6" + address + " &7pinged the server").hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + ListUtils.fromList(names, false, false));

                    for (User user : User.getUsers(false))
                        if (user.inPingSpy())
                            MessageUtils.message(user, message.build());
                }
            }).start();
        } else {
            recentPings.add(address);

            new BukkitRunnable() {
                public void run() {
                    recentPings.remove(address);
                }
            }.runTaskLaterAsynchronously(Core.getInstance(), 6000);
        }
    }

}
