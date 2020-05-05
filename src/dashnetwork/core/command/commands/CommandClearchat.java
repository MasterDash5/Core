package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandClearchat extends CoreCommand {

    public CommandClearchat() {
        super("clearchat", PermissionType.STAFF, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        List<Player> targets = new ArrayList<>();

        if (args.length > 0) {
            List<Player> selector = SelectorUtils.getPlayers(sender, args[0]);

            if (selector != null)
                targets = selector;
        } else
            targets.addAll(Bukkit.getOnlinePlayers());

        if (targets.isEmpty())
            MessageUtils.usage(sender, label, "<player>");
        else {
            for (Player target : targets) {
                for (int i = 0; i < 100; i++)
                    MessageUtils.message(target, " ");

                MessageBuilder message = new MessageBuilder();
                message.append("&6&l» &7Chat was cleared by ");
                message.append("&6" + NameUtils.getDisplayName(sender)).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + sender.getName());

                MessageUtils.message(target, message.build());
            }
        }
    }

}
