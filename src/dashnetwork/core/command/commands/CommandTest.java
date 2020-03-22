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
        builder.addLine("&1blue &2green &3more blue &4red &5purple &6gold &7gray &8more gray &0black &fwhite");
        builder.addLine("&kmagic&r &lbold&r &munder&r &nthrough&r &oitalic");
        builder.addClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "hi there");
        builder.addHoverEvent(HoverEvent.Action.SHOW_TEXT, "hi there");

        MessageUtils.message(sender, builder.build());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }

}
