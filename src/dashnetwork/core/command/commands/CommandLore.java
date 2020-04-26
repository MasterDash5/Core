package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.MaterialUtils;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import dashnetwork.core.utils.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class CommandLore extends CoreCommand {

    public CommandLore() {
        super("lore", PermissionType.ADMIN, false);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 1) {
                String arg = args[0];
                int line;

                try {
                    line = Integer.valueOf(arg) - 1;
                } catch (NumberFormatException exception) {
                    MessageUtils.message(sender, "&6&l» &7Invalid integer: " + arg);
                    return;
                }

                String lore = ChatColor.translateAlternateColorCodes('&', StringUtils.unsplit(args, " ").substring(arg.length() + 1));
                PlayerInventory inventory = player.getInventory();
                ItemStack item = inventory.getItemInMainHand();

                if (!MaterialUtils.isAir(item.getType())) {
                    List<String> lores = item.getLore();

                    if (lores == null)
                        lores = new ArrayList<>();

                    int size = lores.size();

                    if (line > size) {
                        MessageUtils.message(sender, "&6&l» &7There are only &6" + size + " lines");
                        return;
                    } else if (lore.equals("clear")) {
                        lores.remove(line);
                    } else {
                        if (line == size)
                            lores.add(lore);
                        else
                            lores.set(line, lore);
                    }

                    item.setLore(lores);
                    inventory.setItemInMainHand(item);
                    player.updateInventory();
                }
            } else
                MessageUtils.usage(sender, label, "<line> <lore>");
        } else
            MessageUtils.playerCommandOnly();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return new ArrayList<>();
    }

}
