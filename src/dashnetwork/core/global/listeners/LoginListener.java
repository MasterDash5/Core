package dashnetwork.core.global.listeners;

import dashnetwork.core.command.commands.CommandFuckoff;
import dashnetwork.core.utils.DataUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginListener implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        String address = event.getAddress().getHostAddress();
        Map<String, List<String>> offlineList = DataUtils.getOfflineList();
        List<String> accounts = offlineList.getOrDefault(address, new ArrayList<>());

        if (!accounts.contains(uuid)) {
            accounts.add(player.getUniqueId().toString());
            offlineList.put(address, accounts);
        }

        if (CommandFuckoff.getFuckoff() && !player.hasPlayedBefore())
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "&cYou are not allowed to join right now");

        if (event.getResult().equals(PlayerLoginEvent.Result.KICK_BANNED)) {

        }
    }

}
