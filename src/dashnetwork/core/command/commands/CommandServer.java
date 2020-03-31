package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import dashnetwork.core.utils.SenderUtils;
import dashnetwork.core.utils.WorldUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;

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
            String worldName = args[0].toLowerCase().replace("skyblock", "skyworld").replace("pvp", "KitPvP");
            World world = Bukkit.getWorld(worldName);

            if (isWorldAllowed(sender, world))
                target.teleport(world.getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();

            for (World world : Bukkit.getWorlds()) {
                String name = world.getName();

                if (isWorldAllowed(sender, world))
                    completions.add(name.replace("skyworld", "Skyblock").replace("KitPvP", "PvP"));
            }

            return completions;
        }
        return null;
    }

    private boolean isWorldAllowed(CommandSender sender, World world) {
        if (SenderUtils.isStaff(sender) && WorldUtils.isStaffWorld(world))
            return true;

        if (SenderUtils.isAdmin(sender))
            return true;

        return WorldUtils.isPlayerTeleportable(world);
    }

}
