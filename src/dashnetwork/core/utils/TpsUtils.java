package dashnetwork.core.utils;

import dashnetwork.core.Core;
import org.bukkit.scheduler.BukkitRunnable;

public class TpsUtils {

    private static int count = 0;
    private static long[] ticks = new long[600];

    public static void startup() {
        new BukkitRunnable() {
            public void run() {
                ticks[(count % ticks.length)] = System.currentTimeMillis();
                count++;
            }
        }.runTaskTimer(Core.getInstance(), 0, 1);
    }

    public static double getTPS() {
        if (count < 100)
            return -1;

        try {
            int target = (count - 101) % ticks.length;
            long elapsed = System.currentTimeMillis() - ticks[target];

            return 100 / (elapsed / 1000);
        } catch (Exception exception) {
            return -1;
        }
    }

}
