package dashnetwork.core.packet.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.ListeningWhitelist;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.GamePhase;
import dashnetwork.core.Core;
import dashnetwork.core.events.UserPacketEvent;
import dashnetwork.core.utils.PacketEventType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PacketListener extends PacketAdapter {

    private static Core plugin = Core.getInstance();
    private static Set<PacketType> enabledPackets = StreamSupport.stream(PacketType.values().spliterator(), false).filter(type -> type.isSupported() && (type.getProtocol().equals(PacketType.Play.getProtocol()) || type.equals(PacketType.Handshake.Client.SET_PROTOCOL) || type.equals(PacketType.Login.Server.SUCCESS))).collect(Collectors.toSet());

    public PacketListener() {
        super(new AdapterParameteters().gamePhase(GamePhase.BOTH).plugin(plugin).types(enabledPackets).listenerPriority(ListenerPriority.HIGHEST));
    }

    public void start() {
        ProtocolLibrary.getProtocolManager().addPacketListener(this);
    }

    public void stop() {
        ProtocolLibrary.getProtocolManager().removePacketListener(this);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        UserPacketEvent userPacketEvent = new UserPacketEvent(event, PacketEventType.RECEIVED);

        Bukkit.getPluginManager().callEvent(userPacketEvent);

        event.setCancelled(userPacketEvent.isCancelled());
        event.setPacket(userPacketEvent.getPacket());
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        UserPacketEvent userPacketEvent = new UserPacketEvent(event, PacketEventType.SENT);

        Bukkit.getPluginManager().callEvent(userPacketEvent);

        event.setCancelled(userPacketEvent.isCancelled());
        event.setPacket(userPacketEvent.getPacket());
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public ListeningWhitelist getReceivingWhitelist() {
        return ListeningWhitelist.newBuilder().gamePhase(GamePhase.BOTH).types(enabledPackets).priority(ListenerPriority.HIGHEST).build();
    }

    @Override
    public ListeningWhitelist getSendingWhitelist() {
        return ListeningWhitelist.newBuilder().gamePhase(GamePhase.BOTH).types(enabledPackets).priority(ListenerPriority.HIGHEST).build();
    }

}
