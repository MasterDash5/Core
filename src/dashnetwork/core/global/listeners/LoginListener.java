package dashnetwork.core.global.listeners;

import dashnetwork.core.command.commands.CommandFuckoff;
import dashnetwork.core.utils.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginListener implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        PlayerLoginEvent.Result result = event.getResult();

        if (CommandFuckoff.getFuckoff() && !player.hasPlayedBefore())
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "&cYou are not allowed to join right now");

        if (result.equals(PlayerLoginEvent.Result.KICK_FULL) && user.isStaff())
            event.allow();

        if (user.isOwner())
            event.allow();
    }

}
