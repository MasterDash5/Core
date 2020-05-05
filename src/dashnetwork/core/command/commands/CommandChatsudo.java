package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandChatsudo extends CoreCommand {

    public CommandChatsudo() {
        super("chatsudo", PermissionType.OWNER, false);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        List<Player> targets = new ArrayList<>();

        if (args.length > 1) {
            List<Player> selector = SelectorUtils.getPlayers(sender, args[0]);

            if (selector != null)
                targets = selector;
        }

        targets.removeIf(target -> !SenderUtils.canSee(sender, target));

        if (targets.isEmpty())
            MessageUtils.usage(sender, label, "<player> <message>");
        else {
            String message = StringUtils.unsplit(args, " ").substring(args[0].length() + 1);

            for (Player target : targets)
                target.chat(message);
        }
    }

}
