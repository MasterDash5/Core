package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandSignspy extends CoreCommand {

    public CommandSignspy() {
        super("signspy", PermissionType.STAFF);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        Player target = null;

        if (args.length > 0 && SenderUtils.isAdmin(sender))
            target = Bukkit.getPlayer(args[0]);
        else if (sender instanceof Player)
            target = (Player) sender;

        if (target == null || !SenderUtils.canSee(sender, target))
            MessageUtils.usage(sender, label, "<player>");
        else {
            User user = User.getUser(target);
            boolean inSignSpy = !user.inSignSpy();

            user.setInSignSpy(inSignSpy);

            if (inSignSpy)
                MessageUtils.message(target, "&6&l» &7You are now in SignSpy");
            else
                MessageUtils.message(target, "&6&l» &7You are no longer in SignSpy");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1 && SenderUtils.isAdmin(sender))
            return ListUtils.getOnlinePlayers(sender);
        return null;
    }

}
