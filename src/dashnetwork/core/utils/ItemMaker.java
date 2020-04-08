package dashnetwork.core.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.*;

@Deprecated // Bukkit's API is perfectly fine for making items.
public class ItemMaker {

    private Material material;
    private String name;
    private List<String> lore;
    private List<ItemFlag> itemFlags;
    private Map<Enchantment, Integer> enchantments;
    private int amount;
    private boolean unbreakable;

    public ItemMaker(Material material) {
        this.material = material;
        this.amount = 1;
        this.unbreakable = false;
        this.itemFlags = new ArrayList<>();
        this.enchantments = new HashMap<>();
    }

    public ItemStack create() {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            if (name != null)
                itemMeta.setDisplayName(name);

            if (lore != null)
                itemMeta.setLore(lore);

            if (!itemFlags.isEmpty())
                for (ItemFlag flag : itemFlags)
                    itemMeta.addItemFlags(flag);

            if (!enchantments.isEmpty())
                for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet())
                    itemMeta.addEnchant(entry.getKey(), entry.getValue(), true);

            itemMeta.setUnbreakable(unbreakable);
            itemStack.setItemMeta(itemMeta);
        }

        return itemStack;
    }

    public ItemMaker setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemMaker setLore(String... lore) {
        this.lore = Arrays.asList(lore);
        return this;
    }

    public ItemMaker setName(String name) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        return this;
    }

    public ItemMaker setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemMaker setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    public ItemMaker addEnchantment(Enchantment enchantment, int level) {
        this.enchantments.put(enchantment, level);
        return this;
    }
    
    public ItemMaker addItemFlag(ItemFlag flag) {
        this.itemFlags.add(flag);
        return this;
    }
    
    public static ItemMaker fromItem(ItemStack item) {
        ItemMaker maker = new ItemMaker(item.getType());

        if (item.getItemMeta() != null) {
            ItemMeta meta = item.getItemMeta();

            if (meta.getDisplayName() != null)
                maker.setName(meta.getDisplayName());

            if (meta.getLore() != null)
                maker.setLore(meta.getLore());
            
            for (ItemFlag itemFlag : ItemFlag.values()) {
               if (meta.hasItemFlag(itemFlag))
                   maker.addItemFlag(itemFlag);
            }
        }

        maker.setAmount(item.getAmount());
        return maker;
    }
    
    public static ItemStack getPotionItemStack(EnumPotionType potionType, PotionType type, boolean extend, boolean upgraded, int amount) {
        Material material = Material.POTION;
    	
        if (potionType.equals(EnumPotionType.SPLASH)) {
            material = Material.SPLASH_POTION;
        }
        else if (potionType.equals(EnumPotionType.LINGERING)) {
            material = Material.LINGERING_POTION;
        }
    	
        ItemStack potion = new ItemStack(material, amount);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        
        meta.setBasePotionData(new PotionData(type, extend, upgraded));
        potion.setItemMeta(meta);
        
        return potion;
    }
    
    public enum EnumPotionType {
        NORMAL, SPLASH, LINGERING;
    }
}