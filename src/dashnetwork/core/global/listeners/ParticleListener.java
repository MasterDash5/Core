package dashnetwork.core.global.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import dashnetwork.core.events.UserPacketEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ParticleListener implements Listener {

    @EventHandler
    public void onUserPacket(UserPacketEvent event) {
        PacketContainer packet = event.getPacket();

        if (packet.getType().equals(PacketType.Play.Server.WORLD_PARTICLES) && packet.getNewParticles().read(0).getParticle().equals(EnumWrappers.Particle.SWEEP_ATTACK))
            event.setCancelled(true); // Screw sweeping particles.
    }

}
