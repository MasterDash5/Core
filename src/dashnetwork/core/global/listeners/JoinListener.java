package dashnetwork.core.global.listeners;

import dashnetwork.core.Core;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
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
        World world = player.getLocation().getWorld();
        String uuid = player.getUniqueId().toString();
        String address = player.getAddress().getAddress().getHostAddress();
        User user = User.getUser(player);

        for (User online : User.getUsers(false)) {
            if (online.inAutoWelcome()) {
                new BukkitRunnable() {
                    public void run() {
                        online.getPlayer().chat(player.hasPlayedBefore() ? "wb" : "welcome");
                    }
                }.runTaskLater(Core.getInstance(), ThreadLocalRandom.current().nextInt(20, 60));
            }
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
                message.append("&c&lAlt &6" + player.getDisplayName() + " &c&l>&7 hover for list of &6" + alts.size() + " alts").hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + ListUtils.fromList(alts, false, true));

                for (User online : User.getUsers(false))
                    if (online.inAltSpy())
                        MessageUtils.message(online, message.build());
            }
        }).start();

        if (LazyUtils.anyEquals(world.getName(), "Hub", "KitPvP"))
            player.teleport(world.getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);

        if (DataUtils.getOwnerchatList().contains(uuid))
            user.setInOwnerChat(true);

        if (DataUtils.getAdminchatList().contains(uuid))
            user.setInAdminChat(true);

        if (DataUtils.getStaffchatList().contains(uuid))
            user.setInStaffChat(true);

        if (DataUtils.getCommandspyList().contains(uuid))
            user.setInCommandSpy(true);

        if (DataUtils.getSignspyList().contains(uuid))
            user.setInSignSpy(true);

        if (DataUtils.getBookspyList().contains(uuid))
            user.setInBookSpy(true);

        if (DataUtils.getAltspyList().contains(uuid))
            user.setInAltSpy(true);

        if (DataUtils.getPingspyList().contains(uuid))
            user.setInPingSpy(true);

        if (DataUtils.getAutowelcomeList().contains(uuid))
            user.setInAutoWelcome(true);
    }

}
