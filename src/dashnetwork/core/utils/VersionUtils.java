package dashnetwork.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import protocolsupport.api.ProtocolSupportAPI;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;

public class VersionUtils {

    private static PluginManager pluginManager = Bukkit.getPluginManager();
    private static boolean hasProtocolSupport = pluginManager.isPluginEnabled("ProtocolSupport");
    private static boolean hasViaVersion = pluginManager.isPluginEnabled("ViaVersion");

    public static boolean hasProtocolSupport() {
        return hasProtocolSupport;
    }

    public static boolean hasViaVersion() {
        return hasViaVersion;
    }

    public static String getServerVersion() {
        return Bukkit.getBukkitVersion().split("-")[0];
    }

    public static String getPlayerVersion(Player player) {
        String version = getServerVersion();

        if (hasProtocolSupport)
            version = ProtocolSupportAPI.getConnection(player).getVersion().getName();

        if (hasViaVersion) {
            String via = ProtocolVersion.getProtocol(Via.getAPI().getPlayerVersion(player)).getName();

            if (isBefore(via, version, false))
                version = via;
        }

        return version;
    }

    public static String getOldest() {
        if (hasProtocolSupport)
            return "1.4.7";
        return getServerVersion();
    }

    public static String getLatest() {
        if (hasViaVersion) {
            int highest = 0;

            for (ProtocolVersion version : ProtocolVersion.getProtocols()) {
                int id = version.getId();

                if (ProtocolVersion.isRegistered(id) && id > highest)
                    highest = id;
            }

            return ProtocolVersion.getProtocol(highest).getName();
        }

        return getServerVersion();
    }

    public static boolean isAfter(String currentVersion, String futureVersion, boolean trueOnEqual) {
        if (futureVersion.equals(currentVersion))
            return trueOnEqual;

        String[] futureSplit = futureVersion.split("\\.");
        String[] playerSplit = currentVersion.split("\\.");
        int futureLength = futureSplit.length;
        int playerLength = playerSplit.length;
        int length = MathUtils.getLargestInt(futureLength, playerLength);

        for (int i = 0; i < length && (futureLength & playerLength) >= i - 1; i++) {
            int future = Integer.valueOf(futureSplit[i]);
            int current = Integer.valueOf(playerSplit[i]);

            if (current > future)
                return false;
        }

        return true;
    }

    public static boolean isBefore(String currentVersion, String legacyVersion, boolean trueOnEqual) {
        return !isAfter(currentVersion, legacyVersion, trueOnEqual);
    }

    @Deprecated
    public static boolean isLegacy(Player player) {
        return isBefore(getPlayerVersion(player), "1.9", false);
    }

}
