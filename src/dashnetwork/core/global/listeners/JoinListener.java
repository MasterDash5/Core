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
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        String displayname = player.getDisplayName();
        String name = player.getName();
        World world = player.getLocation().getWorld();
        String uuid = player.getUniqueId().toString();
        String address = player.getAddress().getAddress().getHostAddress();
        List<String> deprecatedIpsList = DataUtils.getDeprecatedIpList();
        Map<String, List<String>> offlineList = DataUtils.getOfflineList();
        List<String> accounts = offlineList.getOrDefault(address, new ArrayList<>());

        if (!accounts.contains(uuid) && !DataUtils.getRealjoins().contains(uuid)) {
            accounts.add(player.getUniqueId().toString());
            offlineList.put(address, accounts);
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

        if (!player.hasPlayedBefore()) {
            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» &6Welcome, ");
            message.append("&6" + displayname).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + name);
            message.append("&6 to DashNetwork");

            MessageUtils.broadcast(true, null, PermissionType.NONE, message.build());
        }

        if (deprecatedIpsList.contains(address)) {
            MessageUtils.message(player, "&6&l» &cWe noticed you're using an outdated server IP! The IP you're using could be changed in the future. Please change your IP to &6dashnetwork.mc-srv.com");

            deprecatedIpsList.remove(address);
        }

        String header = ChatColor.translateAlternateColorCodes('&', "&7&l» &6&lDashNetwork &7&l«\n ");
        String footer = ChatColor.translateAlternateColorCodes('&', "\n&6dashnetwork.mc-srv.com");
        player.setPlayerListHeaderFooter(header, footer);

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Objective objective = scoreboard.getObjective(DisplaySlot.PLAYER_LIST);

        if (objective.getName().equals("Core"))
            objective.getScore(player.getName()).setScore(0);

        if (LazyUtils.anyEquals(world.getName(), "Hub", "KitPvP"))
            player.teleport(WorldUtils.getWarp(world), PlayerTeleportEvent.TeleportCause.PLUGIN);

        if (user.isGolden()) {
            user.setLocked(true);

            if (!player.isOnGround()) {
                player.setAllowFlight(true);
                player.setFlying(true);
            }
        }

    }

}
