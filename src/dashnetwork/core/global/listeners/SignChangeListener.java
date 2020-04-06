package dashnetwork.core.global.listeners;

import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.StringUtils;
import dashnetwork.core.utils.User;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location location = block.getLocation();
        String world = location.getWorld().getName();
        String[] lines = event.getLines();
        String coords = location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ();
        String hover = "";

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            if (!StringUtils.clear(line, " ").isEmpty()) {
                if (!hover.isEmpty())
                    hover += "\n";
                hover += "&6" + (i + 1) + ": &7" + line;
            }
        }

        if (!hover.isEmpty()) {
            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» ");
            message.append("&6" + player.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + player.getName());
            message.append(" ");
            message.append("&7placed sign: " + world + " " + coords).clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, coords).hoverEvent(HoverEvent.Action.SHOW_TEXT, hover);

            for (User user : User.getUsers(false))
                if (user.inSignSpy())
                    MessageUtils.message(user, message.build());
        }
    }

}
