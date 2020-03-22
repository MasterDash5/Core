package dashnetwork.core.global.listeners;

import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        for (User user : User.getUsers(false))
            if (user.inCommandSpy())
                MessageUtils.message(user, "&c&lCS &f" + event.getPlayer().getDisplayName() + " &e&l> &b" + event.getMessage());
    }

}
