package dashnetwork.core.skyblock.listeners;

import com.google.common.collect.Iterables;
import dashnetwork.core.Core;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class BlockFormListener implements Listener {

    private Map<Material, Double> blockChances = new HashMap<>();
    private ThreadLocalRandom random = ThreadLocalRandom.current();

    public BlockFormListener() {
        Core plugin = Core.getInstance();
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection("cobble-gen-blocks");

        if (section == null) {
            System.out.println("bruh");
            blockChances.put(Material.COBBLESTONE, 100.0D);
            return;
        }

        for (Map.Entry<String, Object> entry : section.getValues(true).entrySet())
            blockChances.put(Material.getMaterial(entry.getKey().toUpperCase()), (Double) entry.getValue());
    }

    @EventHandler
    public void onBlockForm(BlockFormEvent event) {
        Block block = event.getBlock();
        BlockState state = event.getNewState();

        if (block.getWorld().getName().equals("skyworld") && state.getType() == Material.COBBLESTONE)
            block.setType(getRandomMaterial());
    }

    private Material getRandomMaterial() {
        Set<Material> materials = blockChances.keySet();

        double randomChance = random.nextDouble(100);
        double totalChance = 0;

        for (Material material : materials) {
            totalChance += blockChances.get(material);

            if (totalChance >= randomChance)
                return material;
        }

        return Iterables.getLast(materials);
    }
}