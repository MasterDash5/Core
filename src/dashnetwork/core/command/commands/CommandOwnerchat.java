package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandOwnerchat extends CoreCommand {

    public CommandOwnerchat() {
        super("ownerchat", PermissionType.OWNER);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        boolean hasArgs = args.length > 0;

        if (sender instanceof Player) {
            Player target = null;

            if (hasArgs)
                target = Bukkit.getPlayer(args[0]);
            else if (sender instanceof Player)
                target = (Player) sender;

            User user = User.getUser(target);
            boolean inOwnerChat = !user.inOwnerChat();

            user.setInOwnerChat(inOwnerChat);

            if (inOwnerChat)
                MessageUtils.message(target, "&6&l» &7You are now in OwnerChat");
            else
                MessageUtils.message(target, "&6&l» &7You are no longer in OwnerChat");
        } else {
            if (hasArgs)
                MessageUtils.broadcast(true, null, PermissionType.ADMIN, "&9&lOwner &6Console &6&l> &6" + StringUtils.unsplit(args, " "));
            else
                MessageUtils.usage(sender, label, "<message>");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1)
            return ListUtils.getOnlinePlayers(sender);
        return null;
    }

}
