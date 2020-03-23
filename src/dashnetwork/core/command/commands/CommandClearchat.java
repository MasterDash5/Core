package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import dashnetwork.core.utils.SenderUtils;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandClearchat extends CoreCommand {

    public CommandClearchat() {
        super("clearchat", PermissionType.STAFF);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        for (int i = 0; i < 100; i++)
            MessageUtils.broadcast(false, null, PermissionType.NONE, " ");

        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l» &7Chat was cleared by ");
        builder.append(SenderUtils.getDisplayName(sender)).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + sender.getName());

        MessageUtils.broadcast(true, null, PermissionType.NONE, builder.build());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }

}
