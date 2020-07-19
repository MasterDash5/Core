package dashnetwork.core.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.ColorUtils;
import dashnetwork.core.utils.PermissionType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;

public class CommandTest extends CoreCommand {

    public CommandTest() {
        super("test", PermissionType.OWNER, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            new BukkitRunnable() {
                int i = 0;

                public void run() {
                    int red = (int) (Math.sin(i * 0.2) * 127.5 + 127.5);
                    int green = (int) (Math.sin(i * 0.2 + 2) * 127.5 + 127.5);
                    int blue = (int) (Math.sin(i * 0.2 + 4) * 127.5 + 127.5);
                    Color color = new Color(red, green, blue);
                    String hex = ColorUtils.toHexColor(color);
                    String text = hex + "Testy";

                    player.sendTitle(text, text, 0, 20, 0);

                    i++;

                    if (i >= 128)
                        cancel();
                }
            }.runTaskTimer(plugin, 0, 1);
        }
    }

    @Override
    public CommandNode onTabComplete(LiteralArgumentBuilder builder) {
        return builder.build();
    }

}
