package dashnetwork.core.global.listeners;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dashnetwork.core.utils.DataUtils;
import dashnetwork.core.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import protocolsupport.api.Connection;
import protocolsupport.api.events.PlayerProfileCompleteEvent;

import java.util.Map;
import java.util.UUID;

public class ProfileCompleteListener implements Listener {

    @EventHandler
    public void onPlayerProfileComplete(PlayerProfileCompleteEvent event) {
        Map<UUID, GameProfile> queue = DataUtils.getQueuedRealjoins();
        Connection connection = event.getConnection();
        UUID uuid = connection.getProfile().getUUID();

        if (queue.containsKey(uuid)) {
            Player player = connection.getPlayer();
            GameProfile profile = queue.get(uuid);
            Property property = profile.getProperties().get("textures").iterator().next();

            event.setForcedUUID(profile.getId());
            event.setForcedName(profile.getName());

            // event.getProperties("texture").add(new ProfileProperty("textures", property.getValue(), property.getSignature())); TODO: Add when implemented by ProtocolSupport

            queue.remove(uuid);
        }
    }

}
