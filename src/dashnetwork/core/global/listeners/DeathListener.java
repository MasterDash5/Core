package dashnetwork.core.global.listeners;

import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (!player.getWorld().getName().equalsIgnoreCase("KitPvP")) {
            String name = player.getName();
            String deathmessage = event.getDeathMessage();
            String[] split = deathmessage.split(name);

            if (split.length == 2) {
                MessageBuilder message = new MessageBuilder();
                message.append("&6&l» &7" + split[0]);
                message.append("&6" + player.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + name);
                message.append("&7" + split[1]);

                MessageUtils.broadcast(true, null, PermissionType.NONE, message.build());

                event.setDeathMessage(null);
            } else
                event.setDeathMessage("&6&l» &6" + deathmessage);
        }
    }

}
