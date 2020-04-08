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
        String uuid = player.getUniqueId().toString();
        User user = User.getUser(player);
        boolean inOwnerChat = user.inOwnerChat();
        boolean inAdminChat = user.inAdminChat();
        boolean inStaffChat = user.inStaffChat();
        boolean inCommandSpy = user.inCommandSpy();
        boolean inSignSpy = user.inSignSpy();
        boolean inBookSpy = user.inBookSpy();
        boolean inAltSpy = user.inAltSpy();
        boolean inPingSpy = user.inPingSpy();
        boolean inAutoWelcome = user.inAutoWelcome();
        List<String> ownerchatList = DataUtils.getOwnerchatList();
        List<String> adminchatList = DataUtils.getAdminchatList();
        List<String> staffchatList = DataUtils.getStaffchatList();
        List<String> commandspyList = DataUtils.getCommandspyList();
        List<String> signspyList = DataUtils.getSignspyList();
        List<String> bookspyList = DataUtils.getBookspyList();
        List<String> altspyList = DataUtils.getAltspyList();
        List<String> pingspyList = DataUtils.getPingspyList();
        List<String> autowelcomeList = DataUtils.getAutowelcomeList();
        boolean inOwnerChatList = ownerchatList.contains(uuid);
        boolean inAdminChatList = adminchatList.contains(uuid);
        boolean inStaffChatList = staffchatList.contains(uuid);
        boolean inCommandSpyList = commandspyList.contains(uuid);
        boolean inSignSpyList = signspyList.contains(uuid);
        boolean inBookSpyList = bookspyList.contains(uuid);
        boolean inAltSpyList = altspyList.contains(uuid);
        boolean inPingSpyList = pingspyList.contains(uuid);
        boolean inAutoWelcomeList = autowelcomeList.contains(uuid);

        if (user.isVanished()) {
            event.setQuitMessage(null);

            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» ");
            message.append("&6" + player.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + player.getName());
            message.append("&7 has quit vanished");

            MessageUtils.broadcast(true, null, PermissionType.STAFF, message.build());
        }

        if (inOwnerChat && !inOwnerChatList)
            ownerchatList.add(uuid);
        else if (!inOwnerChat && inOwnerChatList)
            ownerchatList.remove(uuid);

        if (inAdminChat && !inAdminChatList)
            adminchatList.add(uuid);
        else if (!inAdminChat && inAdminChatList)
            adminchatList.remove(uuid);

        if (inStaffChat && !inStaffChatList)
            staffchatList.add(uuid);
        else if (!inStaffChat && inStaffChatList)
            staffchatList.remove(uuid);

        if (inCommandSpy && !inCommandSpyList)
            commandspyList.add(uuid);
        else if (!inCommandSpy && inCommandSpyList)
            commandspyList.remove(uuid);

        if (inSignSpy && !inSignSpyList)
            signspyList.add(uuid);
        else if (!inSignSpy && inSignSpyList)
            signspyList.remove(uuid);

        if (inBookSpy && !inBookSpyList)
            bookspyList.add(uuid);
        else if (!inBookSpy && inBookSpyList)
            bookspyList.remove(uuid);

        if (inAltSpy && !inAltSpyList)
            altspyList.add(uuid);
        else if (!inAltSpy && inAltSpyList)
            altspyList.remove(uuid);

        if (inPingSpy && !inPingSpyList)
            pingspyList.add(uuid);
        else if (!inPingSpy && inPingSpyList)
            pingspyList.remove(uuid);

        if (inAutoWelcome && !inAutoWelcomeList)
            autowelcomeList.add(uuid);
        else if (!inAutoWelcome && inAutoWelcomeList)
            autowelcomeList.remove(uuid);

        user.remove();
    }

}
