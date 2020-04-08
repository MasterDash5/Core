package dashnetwork.core.utils;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

@Deprecated
public class PlayerUtils {

    public static boolean isStaff(Player player) {
        return player.hasPermission("plots.staff") || player.isOp() || isAdmin(player);
    }

    public static boolean isStaff(CommandSender sender) {
        return sender.hasPermission("plots.staff") || sender.isOp() || isAdmin(sender);
    }

    public static boolean isBuilder(Player player) {
        return player.hasPermission("plots.builder") || player.isOp() || isAdmin(player);
    }

    public static boolean isBuilder(CommandSender sender) {
        return sender.hasPermission("plots.builder") || sender.isOp() || isAdmin(sender);
    }

    public static boolean isAdmin(Player player) {
        return player.hasPermission("plots.expire") || isMatt(player) || isDash(player);
    }

    public static boolean isAdmin(CommandSender sender) {
        Player player = getPlayer(sender);

        return sender.hasPermission("plots.expire") || (player != null ? isMatt(player) || isDash(player) : false);
    }

    public static boolean isMatt(Player player) {
        return LazyUtils.anyEquals(player.getUniqueId().toString(), "0e9c49ee-ed25-462f-b7c4-48cd98a30a62", "d1095122-b0d0-4a24-95e6-cac26439372d");
    }

    public static boolean isDash(Player player) {
        return player.getUniqueId().toString().equals("4f771152-ce61-4d6f-9541-1d2d9e725d0e");
    }

    public static boolean isGold(Player player) {
        return player.getUniqueId().toString().equals("bbeb983a-3111-4722-bcf0-e6aafbd5f7d2");
    }

    public static boolean hasBypass(Player player) {
        return isDash(player) || isMatt(player) || LazyUtils.anyEquals(player.getUniqueId().toString(), "9b9aed73-6d61-491a-a127-4200d4a2f91e", "bbeb983a-3111-4722-bcf0-e6aafbd5f7d2");
    }

    public static boolean hasDC(Player player) {
        return hasBypass(player) || player.getUniqueId().toString().equals("457f166e-2c1d-4841-ae68-f21a89987f98") || player.getUniqueId().toString().equals("688badef-4efb-455e-bac6-9d88bc0f0f46");
    }

    public static boolean hasGroundVelocity(Player player) {
        return player.getVelocity().getY() == -0.0784000015258789D;
    }

    public static boolean isHolding(Player player, Material material) {
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();

        return (mainHand != null && mainHand.getType().equals(material)) || (offHand != null && offHand.getType().equals(material));
    }

    public static boolean isHolding(Player player, String materialName) {
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();

        return (mainHand != null && mainHand.getType().name().contains(materialName)) || (offHand != null && offHand.getType().name().contains(materialName));
    }

    public static boolean isHolding(Player player, Enchantment enchantment) {
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();

        return (mainHand != null && mainHand.containsEnchantment(enchantment)) || (offHand != null && offHand.containsEnchantment(enchantment));
    }

    public static int getPing(Player player) {
        return player.spigot().getPing();
    }

    public static Player getPlayer(Object object) {
        if (object instanceof Player)
            return (Player) object;
        return null;
    }

    public static boolean isTheSame(Player one, Player two) {
        return (one.getName().equals(two.getName())
                && one.getUniqueId().equals(two.getUniqueId()));
    }

    public static void sendPacket(Player player, PacketContainer packet, boolean callPacketEvent) {
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet, callPacketEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
