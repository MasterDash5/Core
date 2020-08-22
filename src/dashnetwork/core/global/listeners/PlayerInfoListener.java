package dashnetwork.core.global.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import dashnetwork.core.events.UserPacketEvent;
import dashnetwork.core.utils.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.UUID;

public class PlayerInfoListener implements Listener {

    @EventHandler
    public void onUserPacket(UserPacketEvent event) {
        PacketContainer packet = event.getPacket();

        if (packet.getType().equals(PacketType.Play.Server.PLAYER_INFO)) {
            List<PlayerInfoData> list = packet.getPlayerInfoDataLists().read(0);

            for (int i = 0; i < list.size(); i++) {
                PlayerInfoData data = list.get(i);
                UUID uuid = data.getProfile().getUUID();
                Player player = Bukkit.getPlayer(uuid);

                if (player != null && !player.hasMetadata("NPC")) {
                    User user = User.getUser(player);

                    if (user.isDinnerbone())
                        list.set(i, new PlayerInfoData(new WrappedGameProfile(uuid, "Dinnerbone"), data.getLatency(), data.getGameMode(), data.getDisplayName()));
                }
            }

            packet.getPlayerInfoDataLists().write(0, list);
            event.setPacket(packet);
        }
    }

}
