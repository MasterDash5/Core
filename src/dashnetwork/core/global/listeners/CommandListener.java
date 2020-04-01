package dashnetwork.core.global.listeners;

import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.User;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        for (User user : User.getUsers(false)) {
            if (user.inCommandSpy()) {
                MessageBuilder message = new MessageBuilder();
                message.append("&c&lCS ");
                message.append("&6" + player.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + player.getName());
                message.append(" &e&l> &b" + event.getMessage());

                MessageUtils.message(user, message.build());
            }
        }
    }

}
