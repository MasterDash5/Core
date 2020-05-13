package dashnetwork.core.global.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import dashnetwork.core.Core;
import dashnetwork.core.events.UserPacketEvent;
import dashnetwork.core.utils.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class KeepAliveListener implements Listener {

    @EventHandler
    public void onUserPacket(UserPacketEvent event) {
        PacketContainer packet = event.getPacket();

        if (packet.getType().equals(PacketType.Play.Client.KEEP_ALIVE)) {
            new BukkitRunnable() {
                public void run() {
                    User user = event.getUser();
                    Player player = user.getPlayer();
                    int ping = player.spigot().getPing();

                    Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
                    Objective objective = scoreboard.getObjective(DisplaySlot.PLAYER_LIST);

                    if (objective.getName().equals("Core"))
                        objective.getScore(player.getName()).setScore(ping);
                }
            }.runTaskLaterAsynchronously(Core.getInstance(), 20); // Give the server time to update the player's ping
        }
    }

}
