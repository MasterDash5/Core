package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.PermissionType;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandLogin extends CoreCommand {

    public CommandLogin() {
        super("login", PermissionType.NONE);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» &7Incorrect password! Please try again!").hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6We're not a cracked server dummy");

        sender.sendMessage(message.build());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }

}