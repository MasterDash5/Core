package dashnetwork.core.global.listeners;

import dashnetwork.core.utils.DataUtils;
import dashnetwork.core.utils.LazyUtils;
import dashnetwork.core.utils.User;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = player.getLocation().getWorld();
        String uuid = player.getUniqueId().toString();
        User user = User.getUser(player);

        if (LazyUtils.anyEquals(world.getName(), "Hub", "KitPvP"))
            player.teleport(world.getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);

        if (DataUtils.getOwnerchatList().contains(uuid))
            user.setInOwnerChat(true);

        if (DataUtils.getAdminchatList().contains(uuid))
            user.setInAdminChat(true);

        if (DataUtils.getStaffchatList().contains(uuid))
            user.setInStaffChat(true);

        if (DataUtils.getCommandspyList().contains(uuid))
            user.setInCommandSpy(true);

        if (DataUtils.getSignspyList().contains(uuid))
            user.setInSignSpy(true);

        if (DataUtils.getBookspyList().contains(uuid))
            user.setInBookSpy(true);

        if (DataUtils.getAltspyList().contains(uuid))
            user.setInAltSpy(true);

        if (DataUtils.getPingspyList().contains(uuid))
            user.setInPingSpy(true);
    }

}
