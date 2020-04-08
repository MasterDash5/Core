package dashnetwork.core.global.listeners;

import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class QuitListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);

        if (user.isVanished()) {
            event.setQuitMessage(null);

            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» ");
            message.append("&6" + player.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + player.getName());
            message.append("&7 has quit vanished");

            MessageUtils.broadcast(true, null, PermissionType.STAFF, message.build());
        }

        user.remove();
    }

}
