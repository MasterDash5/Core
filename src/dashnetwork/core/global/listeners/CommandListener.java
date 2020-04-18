package dashnetwork.core.global.listeners;

import dashnetwork.core.Core;
import dashnetwork.core.creative.Creative;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.User;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        String message = event.getMessage();

        if (user.isLocked()) {
            if (message.equalsIgnoreCase("/login " + Core.getInstance().getLockPassword())) {
                user.setLocked(false);
                MessageUtils.message(user, "&6&l» &7Login successful");
            }

            event.setCancelled(true);

            return;
        }

        for (User online : User.getUsers())
            if (online.inCommandSpy())
                MessageUtils.message(online, "&c&lCS &6" + player.getDisplayName() + " &e&l> &b" + message);
    }

}
