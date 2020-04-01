package dashnetwork.core.global.listeners;

import dashnetwork.core.utils.*;
import github.scarsz.discordsrv.DiscordSRV;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        String message = event.getMessage();
        String trimmed = message.length() > 3 ? message.substring((message.substring(3).startsWith(" ") ? 4 : 3)) : "";

        event.setCancelled(true);

        if (user.inOwnerChat())
            ownerChat(player, message);
        else if (user.isOwner() && LazyUtils.anyStartsWith(message.toLowerCase(), "@oc", "@dc"))
            ownerChat(player, trimmed);
        else if (user.inAdminChat())
            adminChat(player, message);
        else if (user.isAdmin() && StringUtils.startsWithIgnoreCase(message, "@ac"))
            adminChat(player, trimmed);
        else if (user.inStaffChat())
            staffChat(player, message);
        else if (user.isStaff() && StringUtils.startsWithIgnoreCase(message, "@sc"))
            staffChat(player, trimmed);
        else
            event.setCancelled(false);

        // Little messy but I can't think of anything better
    }

    private void ownerChat(Player player, String input) {
        MessageBuilder message = new MessageBuilder();
        message.append("&9&lOwner ");
        message.append("&6" + player.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + player.getName());
        message.append("&6&l > &c" + input);

        MessageUtils.broadcast(true, null, PermissionType.OWNER, message.build());
    }

    private void adminChat(Player player, String input) {
        MessageBuilder message = new MessageBuilder();
        message.append("&9&lAdmin ");
        message.append("&6" + player.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + player.getName());
        message.append("&6&l > &3" + message);

        MessageUtils.broadcast(true, null, PermissionType.ADMIN, message.build());
    }

    private void staffChat(Player player, String input) {
        MessageBuilder message = new MessageBuilder();
        message.append("&9&lStaff ");
        message.append("&6" + player.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + player.getName());
        message.append("&6&l > &6" + input);

        MessageUtils.broadcast(true, null, PermissionType.STAFF, message.build());

        if (Bukkit.getPluginManager().isPluginEnabled("DiscordSRV"))
            DiscordSRV.getPlugin().processChatMessage(player, input, "staffchat", false);
    }

}
