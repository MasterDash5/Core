package dashnetwork.core.global.listeners;

import dashnetwork.core.Core;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String displayname = player.getDisplayName();
        String name = player.getName();
        World world = player.getLocation().getWorld();
        String uuid = player.getUniqueId().toString();
        String address = player.getAddress().getAddress().getHostAddress();

        for (User online : User.getUsers()) {
            Player onlinePlayer = online.getPlayer();

            if (online.inAutoWelcome()) {
                new BukkitRunnable() {
                    public void run() {
                        onlinePlayer.chat(player.hasPlayedBefore() ? "wb" : "welcome");
                    }
                }.runTaskLater(Core.getInstance(), ThreadLocalRandom.current().nextInt(40, 100));
            }

            if (online.isVanished())
                player.hidePlayer(Core.getInstance(), onlinePlayer);
        }

        new Thread(() -> { // Bukkit.getOfflinePlayer can be laggy
            List<String> alts = new ArrayList<>();

            for (String account : DataUtils.getOfflineList().getOrDefault(address, new ArrayList<>())) {
                if (!account.equals(uuid)) {
                    OfflinePlayer offline = Bukkit.getOfflinePlayer(UUID.fromString(account));

                    if (offline != null)
                        alts.add(offline.getName());
                }
            }

            if (!alts.isEmpty()) {
                MessageBuilder message = new MessageBuilder();
                message.append("&c&lAlt &6" + displayname + " &c&l>&7 hover for list of &6" + alts.size() + " alts").hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + ListUtils.fromList(alts, false, true));

                for (User online : User.getUsers())
                    if (online.inAltSpy())
                        MessageUtils.message(online, message.build());
            }
        }).start();

        if (!player.hasPlayedBefore()) {
            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» &6Welcome, ");
            message.append("&6" + displayname).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + name);
            message.append("&6 to DashNetwork");

            MessageUtils.broadcast(true, null, PermissionType.NONE, message.build());
        }

        String header = ChatColor.translateAlternateColorCodes('&', "&7&l» &6&lDashNetwork &7&l«\n ");
        String footer = ChatColor.translateAlternateColorCodes('&', "\n&6dashnetwork.mc-srv.com");
        player.setPlayerListHeaderFooter(header, footer);

        if (LazyUtils.anyEquals(world.getName(), "Hub", "KitPvP"))
            player.teleport(WorldUtils.getWarp(world), PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

}
