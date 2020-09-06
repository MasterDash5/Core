package dashnetwork.core.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.GrammarUtils;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ThreadLocalRandom;

public class CommandMommy extends CoreCommand {

    private String[] mommies = { "Dash", "Matt", "Furpy", "Skull", "Golden", "Hannah", "Ryan", "Gibby" };

    public CommandMommy() {
        super("mommy", PermissionType.NONE, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        String member = GrammarUtils.capitalization(label);
        String random = mommies[ThreadLocalRandom.current().nextInt(mommies.length)];

        MessageUtils.message(sender, "&6&l» &7Scanning face for " + member + "...");

        new BukkitRunnable() {

            @Override
            public void run() {
                MessageUtils.message(sender, "&6&l» &7Face identified. Your " + member + " is &6" + random);
            }

        }.runTaskLaterAsynchronously(plugin, 20);
    }

    @Override
    public CommandNode onTabComplete(LiteralArgumentBuilder builder) {
        return builder.build();
    }

}
