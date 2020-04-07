package dashnetwork.core.events;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import dashnetwork.core.utils.User;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UserPacketEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private User user;
    private PacketContainer packet;
    private boolean cancelled;

    public UserPacketEvent(PacketEvent event) {
        super(event.isAsync());

        Player player = event.getPlayer();

        if (event.isPlayerTemporary() || player == null)
            this.user = User.getUser(player);
        else
            this.user = null;

        this.packet = event.getPacket();
        this.cancelled = event.isCancelled();
    }

    public User getUser() {
        return user;
    }

    public PacketContainer getPacket() {
        return packet;
    }

    public void setPacket(PacketContainer packet) {
        this.packet = packet;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
