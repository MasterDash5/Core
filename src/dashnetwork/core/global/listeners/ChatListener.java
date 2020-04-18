package dashnetwork.core.global.listeners;

import dashnetwork.core.utils.*;
import github.scarsz.discordsrv.DiscordSRV;
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

        if (user.isLocked())
            return;

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
        MessageUtils.broadcast(true, null, PermissionType.OWNER, "&9&lOwner &6" + player.getDisplayName() + " &6&l> &c" + input);
    }

    private void adminChat(Player player, String input) {
        MessageUtils.broadcast(true, null, PermissionType.ADMIN, "&9&lAdmin &6" + player.getDisplayName() + " &6&l> &3" + input);
    }

    private void staffChat(Player player, String input) {
        MessageUtils.broadcast(true, null, PermissionType.STAFF, "&9&lStaff &6" + player.getDisplayName() + " &6&l> &6" + input);

        if (Bukkit.getPluginManager().isPluginEnabled("DiscordSRV"))
            DiscordSRV.getPlugin().processChatMessage(player, input, "staffchat", false);
    }

}
