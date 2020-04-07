package dashnetwork.core.global.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import dashnetwork.core.events.UserPacketEvent;
import dashnetwork.core.utils.MaterialUtils;
import dashnetwork.core.utils.User;
import dashnetwork.core.utils.VersionUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityMetadataListener implements Listener {

    @EventHandler
    public void onUserPacket(UserPacketEvent event) {
        PacketContainer packet = event.getPacket();
        PacketType type = packet.getType();
        User user = event.getUser();

        if (user != null) {
            Player player = user.getPlayer();

            if (VersionUtils.isBefore(player, "1.9", false)) {
                boolean isSword = MaterialUtils.isSword(player.getInventory().getItemInMainHand().getType());
                int id = player.getEntityId();

                if (isSword && type.equals(PacketType.Play.Client.BLOCK_PLACE)) {
                    user.setBlocking(true);

                    PacketContainer entitydata = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
                    entitydata.getIntegers().write(0, id);

                    WrappedDataWatcher watcher = new WrappedDataWatcher();
                    Byte flag = WrappedDataWatcher.getEntityWatcher(player).getByte(0);

                    if (flag == null)
                        flag = 0x00;

                    if (player.isSwimming())
                        flag = (byte) (flag - 0x10);

                    flag = (byte) (flag + 0x10);

                    watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class)), flag);

                    entitydata.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

                    for (Player online : Bukkit.getOnlinePlayers()) {
                        if (VersionUtils.isBefore(online, "1.9", false)) {
                            try {
                                ProtocolLibrary.getProtocolManager().sendServerPacket(online, entitydata, false);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }
                    }
                }

                if (type.equals(PacketType.Play.Client.BLOCK_DIG)) {
                    user.setBlocking(false);

                    PacketContainer entitydata = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
                    entitydata.getIntegers().write(0, id);

                    WrappedDataWatcher watcher = new WrappedDataWatcher();
                    Byte flag = WrappedDataWatcher.getEntityWatcher(player).getByte(0);

                    if (flag == null)
                        flag = 0x00;

                    watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class)), flag);

                    entitydata.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

                    for (Player online : Bukkit.getOnlinePlayers()) {
                        if (VersionUtils.isBefore(online, "1.9", false)) {
                            try {
                                ProtocolLibrary.getProtocolManager().sendServerPacket(online, entitydata, false);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }
                    }
                }

                if (packet.getType().equals(PacketType.Play.Server.ENTITY_METADATA)) {
                    Player target = null;

                    for (Player online : Bukkit.getOnlinePlayers())
                        if (target.getEntityId() == packet.getIntegers().read(0))
                            target = online;

                    if (target != null) {
                        User targetUser = User.getUser(target);

                        if (targetUser.isBlocking()) {
                            WrappedDataWatcher watcher = new WrappedDataWatcher();
                            Byte flag = WrappedDataWatcher.getEntityWatcher(player).getByte(0);

                            if (flag == null)
                                flag = 0x00;

                            flag = (byte) (flag + 0x10);

                            watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class)), flag);

                            packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
                        }
                    }
                }
            }
        }
    }

}
