package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandFakeop extends CoreCommand {

    public CommandFakeop() {
        super("fakeop", PermissionType.ADMIN, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        List<Player> targets = new ArrayList<>();

        if (args.length > 0) {
            List<Player> selector = SelectorUtils.getPlayers(sender, args[0]);

            if (selector != null)
                targets = selector;
        }

        targets.removeIf(target -> !SenderUtils.canSee(sender, target));

        if (targets.isEmpty())
            MessageUtils.usage(sender, label, "<player>");
        else {
            for (Player target : targets)
                MessageUtils.message(target, "&7&o[" + sender.getName() + ": Opped " + target.getName() + "]");

            String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(targets), false ,false);
            String names = ListUtils.fromList(NameUtils.toNames(targets), false, false);

            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» &7Fake opped ");
            message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);

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
