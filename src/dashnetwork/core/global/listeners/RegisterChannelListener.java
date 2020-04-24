package dashnetwork.core.global.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dashnetwork.core.Core;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRegisterChannelEvent;

public class RegisterChannelListener implements Listener {

    @EventHandler
    public void onRegisterChannel(PlayerRegisterChannelEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        String channel = event.getChannel();

        if (channel.equals("wdl:request")) {
            ByteArrayDataOutput output = ByteStreams.newDataOutput();
            output.writeInt(0);
            output.writeBoolean(user.isOwner());

            player.sendPluginMessage(Core.getInstance(), "wdl:control", output.toByteArray());
        }

        if (user.isDash())
            MessageUtils.message(user, channel);
    }

}
