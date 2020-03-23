package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandTest extends CoreCommand {

    public CommandTest() {
        super("test", PermissionType.OWNER);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&1blue &2green &3more blue &4red &5purple &6gold")
                .hoverEvent(HoverEvent.Action.SHOW_TEXT, "hi")
                .clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "ola");

        MessageUtils.message(sender, builder.build());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }

}
