package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandPlayerinfo extends CoreCommand {

    public CommandPlayerinfo() {
        super("playerinfo", PermissionType.STAFF, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        Player target = null;

        if (args.length > 0)
            target = SelectorUtils.getPlayer(sender, args[0]);

        if (target == null || !SenderUtils.canSee(sender, target))
            MessageUtils.usage(sender, label, "<player>");
        else {
            User user = User.getUser(target);
            Location location = target.getLocation();
            String displayname = target.getDisplayName();
            String uuid = target.getUniqueId().toString();
            String ip = target.getAddress().getAddress().getHostAddress();
            String client = user.getClient();
            String version = VersionUtils.getPlayerVersion(target);
            String world = target.getWorld().getName();
            String gamemode = GrammarUtils.capitalization(target.getGameMode().name());
            String coords = location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ();

            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» &6" + GrammarUtils.possessive(target.getName()) + "&7 player info");
            message.append("\n&7 - Display Name: &6" + displayname).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&7Click to copy &6" + displayname).clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, displayname.replace("§", "&"));
            message.append("\n&7 - UUID: &6" + uuid).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&7Click to copy &6" + uuid).clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, uuid);

            if (SenderUtils.isAdmin(sender))
                message.append("\n&7 - IP: &6" + ip).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&7Click to copy &6" + ip).clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, ip);

            message.append("\n&7 - Client: &6" + client + " &7Version: &6" + version);
            message.append("\n&7 - World: &6" + world + " &7Gamemode: &6" + gamemode);
            message.append("\n&7 - Coords: &6" + coords).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&7Click to copy &6" + coords).clickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, coords);

            MessageUtils.message(sender, message.build());
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1)
            return null;
        return new ArrayList<>();
    }

}
