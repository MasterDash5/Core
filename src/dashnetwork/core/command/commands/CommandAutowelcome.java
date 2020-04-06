package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandAutowelcome extends CoreCommand {

    public CommandAutowelcome() {
        super("autowelcome", PermissionType.OWNER, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        List<Player> targets = new ArrayList<>();
        Player player = sender instanceof Player ? (Player) sender : null;

        if (args.length > 0) {
            List<Player> selector = SelectorUtils.getPlayers(sender, args[0]);

            if (selector != null)
                targets.addAll(selector);
        } else if (player != null)
            targets.add(player);

        for (Player target : targets)
            if (!SenderUtils.canSee(sender, target))
                targets.remove(target);

        if (targets.isEmpty())
            MessageUtils.usage(sender, label, "&6&l» &7No players found");
        else {
            for (Player target : targets) {
                User user = User.getUser(target);
                boolean inAutoWelcome = !user.inAutoWelcome();

                user.setInAutoWelcome(inAutoWelcome);

                if (inAutoWelcome)
                    MessageUtils.message(target, "&6&l» &7You are now in AutoWelcome");
                else
                    MessageUtils.message(target, "&6&l» &7You are no longer in AutoWelcome");
            }

            if (player == null || ListUtils.containsOtherThan(targets, player)) {
                targets.remove(player);

                String displaynames = ListUtils.fromList(ListUtils.toDisplayNames(targets), false, false);
                String names = ListUtils.fromList(ListUtils.toNames(targets), false, false);

                MessageBuilder message = new MessageBuilder();
                message.append("&6&l» ");
                message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
                message.append("&7 toggled AutoWelcome");

                MessageUtils.message(sender, message.build());
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length > 0)
            return ListUtils.getOnlinePlayers(sender);
        return null;
    }

}
