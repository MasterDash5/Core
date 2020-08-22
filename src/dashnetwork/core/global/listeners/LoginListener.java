package dashnetwork.core.global.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import dashnetwork.core.command.commands.CommandFuckoff;
import dashnetwork.core.events.UserPacketEvent;
import dashnetwork.core.utils.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.UUID;

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

    @EventHandler
    public void onUserPacket(UserPacketEvent event) {
        PacketContainer packet = event.getPacket();

        if (packet.getType().equals(PacketType.Login.Server.SUCCESS)) {
            WrappedGameProfile profile = packet.getGameProfiles().read(0);
            UUID uuid = profile.getUUID();

            if (uuid.toString().equals("bbeb983a-3111-4722-bcf0-e6aafbd5f7d2"))
                packet.getGameProfiles().write(0, new WrappedGameProfile(uuid, "Dinnerbone"));

            event.setPacket(packet);
        }
    }

}
