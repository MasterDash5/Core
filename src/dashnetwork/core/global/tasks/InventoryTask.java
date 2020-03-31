package dashnetwork.core.global.tasks;

import dashnetwork.core.utils.ArrayUtils;
import dashnetwork.core.utils.LazyUtils;
import dashnetwork.core.utils.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InventoryTask extends BukkitRunnable {

    private Material[] blacklist = { Material.COMMAND_BLOCK, Material.CHAIN_COMMAND_BLOCK, Material.REPEATING_COMMAND_BLOCK, Material.COMMAND_BLOCK_MINECART, Material.STRUCTURE_BLOCK, Material.STRUCTURE_VOID, Material.JIGSAW, Material.BARRIER, Material.DEBUG_STICK };

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            User user = User.getUser(player);

            if (!user.isAdmin()) {
                PlayerInventory inventory = player.getInventory();

                for (ItemStack item : inventory.getContents()) {
                    if (item != null) {
                        ItemStack clone = item.clone();
                        Material material = item.getType();
                        ItemMeta meta = item.getItemMeta();
                        Set<ItemFlag> flags = item.getItemFlags();
                        int maxStackSize = item.getMaxStackSize();

                        if (ArrayUtils.contains(blacklist, material)) {
                            inventory.remove(item);
                            continue;
                        }

                        if (item.getAmount() > maxStackSize)
                            item.setAmount(maxStackSize);

                        for (Map.Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()) {
                            Enchantment enchant = entry.getKey();
                            int level = entry.getValue();

                            if (enchant.canEnchantItem(item) || level > enchant.getMaxLevel())
                                item.removeEnchantment(enchant);
                        }

                        if (!flags.isEmpty())
                            for (ItemFlag flag : flags)
                                item.removeItemFlags(flag);

                        if (meta.hasAttributeModifiers())
                            for (Attribute attribute : meta.getAttributeModifiers().keySet())
                                meta.removeAttributeModifier(attribute);

                        if (meta.hasCustomModelData())
                            meta.setCustomModelData(null);

                        if (meta.hasDestroyableKeys())
                            meta.setDestroyableKeys(new ArrayList<>());

                        if (meta.hasPlaceableKeys())
                            meta.setPlaceableKeys(new ArrayList<>());

                        if (meta.hasDisplayName()) {
                            String display = meta.getDisplayName();
                            String stripped = ChatColor.stripColor(display);

                            if (display.length() > 35 || !display.equals(ChatColor.ITALIC + stripped))
                                meta.setDisplayName(item.getI18NDisplayName());
                        }

                        if (meta.hasLore())
                            meta.setLore(null);

                        if (meta.isUnbreakable())
                            meta.setUnbreakable(false);

                        item.setItemMeta(meta);

                        if (item != clone) {
                            inventory.remove(clone);
                            inventory.addItem(item);
                        }
                    }
                }
            }
        }
    }

}
