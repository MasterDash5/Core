package dashnetwork.core.task.tasks;

import dashnetwork.core.task.Task;
import dashnetwork.core.utils.User;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class SpinTask extends Task {

    public SpinTask() {
        super(Type.REPEAT, 0, 1, false);
    }

    @Override
    public void run() {
        for (User user : User.getUsers()) {
            if (user.isSpinning()) {
                Player player = user.getPlayer();
                Location location = player.getLocation().clone();

                location.setYaw(location.getYaw() + 0.5F);
                player.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
            }
        }
    }

}
