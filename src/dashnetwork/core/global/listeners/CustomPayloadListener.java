package dashnetwork.core.global.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.MinecraftKey;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dashnetwork.core.Core;
import dashnetwork.core.events.UserPacketEvent;
import dashnetwork.core.utils.User;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CustomPayloadListener implements Listener {

    @EventHandler
    public void onUserPacket(UserPacketEvent event) {
        PacketContainer packet = event.getPacket();
        PacketType type = packet.getType();

        if (type.equals(PacketType.Play.Server.CUSTOM_PAYLOAD)) {
            MinecraftKey channel = packet.getMinecraftKeys().read(0);

            if (channel.getFullKey().equals("minecraft:brand")) {
                String serverName = "DashNetwork";
                ByteBuf buffer = Unpooled.buffer();
                byte[] array = serverName.getBytes();

                int length = array.length;
                int part;

                while (true) {
                    part = length & 0x7F;
                    length >>>= 7;

                    if (length != 0)
                        part |= 0x80;

                    buffer.writeByte(part);

                    if (length == 0)
                        break;
                }

                buffer.writeBytes(array);
                packet.getModifier().withType(ByteBuf.class).write(0, MinecraftReflection.getPacketDataSerializer(buffer));

                event.setPacket(packet);
            }
        }

        if (type.equals(PacketType.Play.Client.CUSTOM_PAYLOAD)) {
            MinecraftKey channel = packet.getMinecraftKeys().read(0);
            User user = event.getUser();
            Player player = user.getPlayer();

            switch (channel.getFullKey()) {
                case "minecraft:brand":
                    ByteBuf buffer = (ByteBuf) packet.getModifier().withType(ByteBuf.class).read(0);
                    String data = new String(buffer.array()).toLowerCase();

                    if (data.equals("forge"))
                        user.setClient("Forge");

                    break;
                case "l:permissionsrepl":
                    user.setClient("LiteLoader");
                    break;
                case "fml:handshake":
                    user.setClient("Forge");
                    break;
                case "wdl:request":
                    ByteArrayDataOutput output = ByteStreams.newDataOutput();
                    output.writeInt(0);
                    output.writeBoolean(user.isOwner());

                    player.sendPluginMessage(Core.getInstance(), "wdl:control", output.toByteArray());
                    break;
            }
        }
    }

}
