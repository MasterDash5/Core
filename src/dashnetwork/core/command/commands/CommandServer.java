package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import dashnetwork.core.utils.SenderUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CommandServer extends CoreCommand {

    public CommandServer() {
        super("server", PermissionType.NONE);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        Player target = null;

        if (args.length > 1 && SenderUtils.isAdmin(sender))
            target = Bukkit.getPlayer(args[0]);
        else if (sender instanceof Player)
            target = (Player) sender;

        if (target == null || !SenderUtils.canSee(sender, target))
            MessageUtils.usage(sender, label, "<server>");
        else {
            // TODO: Make the teleport part of /server
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1) {
            if (SenderUtils.isOwner(sender)) {
                return Bukkit.getWorlds().stream().map(world -> Objects.toString(world.getName(), null)).collect(Collectors.toList());
            } else if (SenderUtils.isStaff(sender)) {
                return Arrays.asList("BuildTeamWorld", "Creative", "Hub", "Skyblock", "Survival", "Prison", "PvP", "WIP");
            } else
                return Arrays.asList("Creative", "Hub", "Skyblock", "Survival", "Prison", "PvP");
        }
        return null;
    }

}
