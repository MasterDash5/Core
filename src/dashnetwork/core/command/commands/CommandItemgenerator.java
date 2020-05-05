package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

public class CommandItemgenerator extends CoreCommand {

    public CommandItemgenerator() {
        super("itemgenerator", PermissionType.OWNER, false);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack generated = new ItemStack(Material.FIREWORK_ROCKET);
            FireworkMeta itemMeta = (FireworkMeta) generated.getItemMeta();
            FireworkEffect effect = FireworkEffect.builder().withColor(Color.WHITE).build();

            for (int i = 0; i < 10000; i++)
                itemMeta.addEffect(effect);

            itemMeta.setPower(127);

            generated.setItemMeta(itemMeta);

            player.getInventory().addItem(generated);
        } else
            MessageUtils.playerCommandOnly();
    }

}
