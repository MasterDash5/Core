package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

import java.util.Arrays;
import java.util.List;

public class CommandTest extends CoreCommand {

    public CommandTest() {
        super("test", PermissionType.OWNER, false);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (args.length > 0) {
            List<Entity> entities = SelectorUtils.getEntities(sender, args[0]);

            MessageUtils.message(sender, "Return: " + entities);
        } else
            MessageUtils.usage(sender, label, "<selector>");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Arrays.asList("Tab complete test");
    }

}
