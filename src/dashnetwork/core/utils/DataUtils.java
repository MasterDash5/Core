package dashnetwork.core.utils;

import com.mojang.authlib.GameProfile;
import dashnetwork.core.Core;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DataUtils {

    private static Map<String, List<String>> offlineList = new HashMap<>();
    private static Map<String, String> survivalLocationList = new HashMap<>();
    private static Map<UUID, GameProfile> queuedRealjoins = new HashMap<>();
    private static List<String> ownerchatList = new ArrayList<>();
    private static List<String> adminchatList = new ArrayList<>();
    private static List<String> staffchatList = new ArrayList<>();
    private static List<String> commandspyList = new ArrayList<>();
    private static List<String> signspyList = new ArrayList<>();
    private static List<String> bookspyList = new ArrayList<>();
    private static List<String> altspyList = new ArrayList<>();
    private static List<String> pingspyList = new ArrayList<>();
    private static List<String> deprecatedIpList = new ArrayList<>();
    private static Core plugin = Core.getInstance();
    private static File dataFile;
    private static FileConfiguration dataConfig;

    public static void startup() {
        String fileLocation = "plugins/Core/data.yml";
        File file = new File(fileLocation);

        if (!file.exists() && plugin.getResource(fileLocation) != null) {
            file.getParentFile().mkdirs();
            plugin.saveResource(fileLocation, false);
        }

        dataFile = file;
        dataConfig = YamlConfiguration.loadConfiguration(file);

        if (dataConfig.contains("addresses"))
            for (String address : dataConfig.getConfigurationSection("addresses").getKeys(false))
                offlineList.put(address.replace("-", "."), dataConfig.getStringList("addresses." + address));

        if (dataConfig.contains("survivallocation"))
            for (String uuid : dataConfig.getConfigurationSection("survivallocation").getKeys(false))
                survivalLocationList.put(uuid, dataConfig.getString("survivallocation." + uuid));

        if (dataConfig.contains("ownerchat"))
            ownerchatList.addAll(dataConfig.getStringList("ownerchat"));

        if (dataConfig.contains("adminchat"))
            adminchatList.addAll(dataConfig.getStringList("adminchat"));

        if (dataConfig.contains("staffchat"))
            staffchatList.addAll(dataConfig.getStringList("staffchat"));

        if (dataConfig.contains("commandspy"))
            commandspyList.addAll(dataConfig.getStringList("commandspy"));

        if (dataConfig.contains("signspy"))
            signspyList.addAll(dataConfig.getStringList("signspy"));

        if (dataConfig.contains("bookspy"))
            bookspyList.addAll(dataConfig.getStringList("bookspy"));

        if (dataConfig.contains("altspy"))
            altspyList.addAll(dataConfig.getStringList("altspy"));

        if (dataConfig.contains("pingspy"))
            pingspyList.addAll(dataConfig.getStringList("pingspy"));
    }

    public static void save() {
        for (Map.Entry<String, List<String>> entry : offlineList.entrySet()) {
            String address = entry.getKey();
            List<String> uuids = entry.getValue();

            dataConfig.set("addresses." + address.replace(".", "-"), uuids);
        }

        for (Map.Entry<String, String> entry : survivalLocationList.entrySet()) {
            String uuid = entry.getKey();
            String location = entry.getValue();

            dataConfig.set("survivallocation." + uuid, location);
        }

        dataConfig.set("ownerchat", ownerchatList);
        dataConfig.set("adminchat", adminchatList);
        dataConfig.set("staffchat", staffchatList);
        dataConfig.set("commandspy", commandspyList);
        dataConfig.set("signspy", signspyList);
        dataConfig.set("bookspy", bookspyList);
        dataConfig.set("altspy", altspyList);
        dataConfig.set("pingspy", pingspyList);

        try {
            dataConfig.save(dataFile);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static FileConfiguration getDataConfig() {
        return dataConfig;
    }

    public static Map<String, List<String>> getOfflineList() {
        return offlineList;
    }

    public static Map<String, String> getSurvivalLocationList() {
        return survivalLocationList;
    }

    public static Map<UUID, GameProfile> getQueuedRealjoins() {
        return queuedRealjoins;
    }

    public static List<String> getOwnerchatList() {
        return ownerchatList;
    }

    public static List<String> getAdminchatList() {
        return adminchatList;
    }

    public static List<String> getStaffchatList() {
        return staffchatList;
    }

    public static List<String> getCommandspyList() {
        return commandspyList;
    }

    public static List<String> getSignspyList() {
        return signspyList;
    }

    public static List<String> getBookspyList() {
        return bookspyList;
    }

    public static List<String> getAltspyList() {
        return altspyList;
    }

    public static List<String> getPingspyList() {
        return pingspyList;
    }

    public static List<String> getDeprecatedIpList() {
        return deprecatedIpList;
    }

}
