package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandAdminchat extends CoreCommand {

    public CommandAdminchat() {
        super("adminchat", PermissionType.ADMIN);
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
            boolean inAdminChat = !user.inAdminChat();

            user.setInAdminChat(inAdminChat);

            if (inAdminChat)
                MessageUtils.message(target, "&6&l» &7You are now in AdminChat");
            else
                MessageUtils.message(target, "&6&l» &7You are no longer in AdminChat");
        } else {
            if (hasArgs)
                MessageUtils.broadcast(true, null, PermissionType.ADMIN, "&9&lAdmin &6Console &6&l> &6" + StringUtils.unsplit(args, " "));
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
