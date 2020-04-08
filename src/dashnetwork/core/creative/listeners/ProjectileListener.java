package dashnetwork.core.creative.listeners;

import dashnetwork.core.utils.User;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.projectiles.ProjectileSource;

public class ProjectileListener implements Listener {

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();
        ProjectileSource source = projectile.getShooter();

        if (source instanceof Player) {
            Player player = (Player) source;
            User user = User.getUser(player);

            if (user.isAdmin())
                return;
        }

        event.setCancelled(true);
    }

}
