package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandVersionlist extends CoreCommand {

    public CommandVersionlist() {
        super("versionlist", PermissionType.NONE);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        Map<String, List<String>> versionlist = new HashMap<>();
        String protocolsupport = VersionUtils.hasProtocolSupport() ? "Yes" : "No";
        String viaversion = VersionUtils.hasViaVersion() ? "Yes" : "No";

        for (Player online : Bukkit.getOnlinePlayers()) {
            if (!SenderUtils.canSee(sender, online))
                continue;

            String version = VersionUtils.getPlayerVersion(online);
            List<String> players = versionlist.getOrDefault(version, new ArrayList<>());

            players.add(online.getName());
            versionlist.put(version, players);
        }

        MessageBuilder message = new MessageBuilder();

        for (Map.Entry<String, List<String>> entry : versionlist.entrySet())
            message.append("&6&l» &7[&6" + entry.getKey() + "&7] " + ListUtils.fromList(entry.getValue(), false, true))
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6ProtocolSupport: &7" + protocolsupport + "\n&6ViaVersion: &7" + viaversion);

        if (message.isEmpty())
            MessageUtils.message(sender, "&6&l» &7Currently no online players");
        else
            MessageUtils.message(sender, message.build());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }

}
