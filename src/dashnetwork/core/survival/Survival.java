package dashnetwork.core.survival;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class Survival {

    public static World getWorld() {
        return Bukkit.getWorld("Survival");
    }

    public static World getNether() {
        return Bukkit.getWorld("Survival_nether");
    }

    public static World getEnd() {
        return Bukkit.getWorld("Survival_the_end");
    }

    public Survival() {}

}
