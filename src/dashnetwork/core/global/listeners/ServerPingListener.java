package dashnetwork.core.global.listeners;

import dashnetwork.core.Core;
import dashnetwork.core.utils.*;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.*;

public class ServerPingListener implements Listener {

    private List<String> recentPings = new ArrayList<>();

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        String address = event.getAddress().getHostAddress();
        String motd = "&6DashNetwork &6&l» &7[" + VersionUtils.getOldest() + " - " + VersionUtils.getLatest() + "]"; // TODO: Think of something better to put here

        event.setMotd(ChatColor.translateAlternateColorCodes('&', motd));

        if (!recentPings.contains(address)) {
            recentPings.add(address);

            new BukkitRunnable() {
                public void run() {
                    recentPings.remove(address);
                }
            }.runTaskLaterAsynchronously(Core.getInstance(), 6000);

            new Thread(() -> {
                Map<String, List<String>> addresses = DataUtils.getOfflineList();

                if (addresses.containsKey(address) && !Bukkit.getBanList(BanList.Type.IP).isBanned(address)) {
                    List<String> names = new ArrayList<>();

                    for (String uuid : addresses.get(address))
                        names.add(Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName());

                    String fromNames = ListUtils.fromList(names, false, true);
                    String date = new SimpleDateFormat("MM/dd/yyy HH:mm:ss").format(Calendar.getInstance().getTime());

                    MessageBuilder message = new MessageBuilder();
                    message.append("&c&lPS &6" + address + " &7pinged the server").hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + fromNames);

                    for (User user : User.getUsers())
                        if (user.inPingSpy())
                            MessageUtils.message(user, message.build());

                    if (Bukkit.getPluginManager().isPluginEnabled("DiscordSRV")) {
                        TextChannel channel = DiscordSRV.getPlugin().getDestinationTextChannelForGameChannelName("pingspy");
                        channel.sendMessage(date + "\t" + address + "\t" + fromNames).queue();
                    }
                }
            }).start();
        }
    }

}
