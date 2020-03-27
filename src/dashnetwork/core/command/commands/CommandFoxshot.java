package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CommandFoxshot extends CoreCommand {

    public CommandFoxshot() {
        super("foxshot", PermissionType.ADMIN);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location location = player.getEyeLocation();
            final Fox fox = (Fox) player.getWorld().spawnEntity(location, EntityType.FOX); // Using final speeds up the fox velocity
            Fox.Type[] types = Fox.Type.values();

            fox.setBaby();
            fox.setFoxType(types[ThreadLocalRandom.current().nextInt(types.length)]);
            fox.setInvulnerable(true);
            fox.setVelocity(location.getDirection().multiply(2));

            new BukkitRunnable() {
                public void run() {
                    fox.getLocation().createExplosion(0);
                    fox.remove();
                }
            }.runTaskLater(plugin, 20);
        } else
            MessageUtils.playerCommandOnly();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }

}
