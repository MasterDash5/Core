package dashnetwork.core.global.listeners;

import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import dashnetwork.core.utils.User;
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
        String displayname = player.getDisplayName();

        if (user.inOwnerChat()) {
            MessageUtils.broadcast(true, null, PermissionType.OWNER, "&9&lOwner &6" + displayname + " &6&l> &6" + message);
            event.setCancelled(true);
        } else if (user.inAdminChat()) {
            MessageUtils.broadcast(true, null, PermissionType.ADMIN, "&9&lAdmin &6" + displayname + " &6&l> &6" + message);
            event.setCancelled(true);
        } else if (user.inStaffChat()) {
            MessageUtils.broadcast(true, null, PermissionType.STAFF, "&9&lStaff &6" + displayname + " &6&l> &6" + message);
            event.setCancelled(true);
        }
    }

}
