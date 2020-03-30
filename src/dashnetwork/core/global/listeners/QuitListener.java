package dashnetwork.core.global.listeners;

import dashnetwork.core.utils.DataUtils;
import dashnetwork.core.utils.User;
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
        String uuid = player.getUniqueId().toString();
        User user = User.getUser(player);
        List<String> ownerchatList = DataUtils.getOwnerchatList();
        List<String> adminchatList = DataUtils.getAdminchatList();
        List<String> staffchatList = DataUtils.getStaffchatList();
        List<String> commandspyList = DataUtils.getCommandspyList();
        List<String> signspyList = DataUtils.getSignspyList();
        List<String> bookspyList = DataUtils.getBookspyList();
        List<String> altspyList = DataUtils.getAltspyList();
        List<String> pingspyList = DataUtils.getPingspyList();

        if (user.inOwnerChat() && !ownerchatList.contains(uuid))
            ownerchatList.add(uuid);

        if (user.inAdminChat() && !adminchatList.contains(uuid))
            adminchatList.add(uuid);

        if (user.inStaffChat() && !staffchatList.contains(uuid))
            staffchatList.add(uuid);

        if (user.inCommandSpy() && !commandspyList.contains(uuid))
            commandspyList.add(uuid);

        if (user.inSignSpy() && !signspyList.contains(uuid))
            signspyList.add(uuid);

        if (user.inBookSpy() && !bookspyList.contains(uuid))
            bookspyList.add(uuid);

        if (user.inAltSpy() && !altspyList.contains(uuid))
            altspyList.add(uuid);

        if (user.inPingSpy() && !pingspyList.contains(uuid))
            pingspyList.add(uuid);

        user.remove();
    }

}
