package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import dashnetwork.core.utils.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandLore extends CoreCommand {

    public CommandLore() {
        super("lore", PermissionType.ADMIN, false);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                String[] lore = ChatColor.translateAlternateColorCodes('&', StringUtils.unsplit(args, " ")).split("\n");
                PlayerInventory inventory = player.getInventory();
                ItemStack item = inventory.getItemInMainHand();

                item.setLore(Arrays.asList(lore));

                inventory.setItemInMainHand(item);
                player.updateInventory();
            } else
                MessageUtils.usage(sender, label, "<lore>");
        } else
            MessageUtils.playerCommandOnly();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return new ArrayList<>();
    }

}
