package dashnetwork.core.global.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import dashnetwork.core.events.UserPacketEvent;
import dashnetwork.core.utils.DataUtils;
import dashnetwork.core.utils.StringUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class HandshakeListener implements Listener {

    @EventHandler
    public void onUserPacket(UserPacketEvent event) {
        PacketContainer packet = event.getPacket();

        if (packet.getType().equals(PacketType.Handshake.Client.SET_PROTOCOL)) {
            String serverIp = "dashnetwork.mc-srv.com";
            String address = event.getPlayer().getAddress().getAddress().getHostAddress();
            String hostname = packet.getStrings().read(0);
            PacketType.Protocol next = packet.getProtocols().read(0);

            if (next.equals(PacketType.Protocol.LOGIN) && !StringUtils.startsWithIgnoreCase(hostname, serverIp)) {
                List<String> deprecatedIpsList = DataUtils.getDeprecatedIpList();

                if (!deprecatedIpsList.contains(address))
                    deprecatedIpsList.add(address);
            }
        }
    }

}
