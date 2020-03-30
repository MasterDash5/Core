package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.GrammarUtils;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CommandMommy extends CoreCommand {

    private String[] mommies = { "Dash", "Matt", "Furp", "Skull", "Golden", "Hannah" };

    public CommandMommy() {
        super("mommy", PermissionType.NONE);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        String member = GrammarUtils.capitalization(label);
        String random = mommies[ThreadLocalRandom.current().nextInt(mommies.length)];

        MessageUtils.message(sender, "&6&l» &7Scanning face for " + member + "...");

        new BukkitRunnable() {
            public void run() {
                MessageUtils.message(sender, "&6&l» &7Face identified. Your " + member + " is &6" + random);
            }
        }.runTaskLaterAsynchronously(plugin, 20);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }

}
