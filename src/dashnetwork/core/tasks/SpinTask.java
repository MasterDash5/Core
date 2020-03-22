package dashnetwork.core.tasks;

import dashnetwork.core.utils.User;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class SpinTask extends BukkitRunnable {

    @Override
    public void run() {
        for (User user : User.getUsers(false)) {
            if (user.isSpinning()) {
                Player player = user.getPlayer();
                Location location = player.getLocation().clone();

                location.setYaw(location.getYaw() + 0.5F);
                player.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
            }
        }
    }

}
