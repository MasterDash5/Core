package dashnetwork.core.command.commands;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.earth2me.essentials.UserMap;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.DataUtils;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CommandImport extends CoreCommand {

    public CommandImport() {
        super("import", PermissionType.OWNER, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        MessageUtils.message(sender, "&6&l» &7Importing users from Essentials...");

        Map<String, List<String>> offlineList = DataUtils.getOfflineList();
        Essentials essentials = Essentials.getPlugin(Essentials.class);
        UserMap map = essentials.getUserMap();

        for (UUID uuid : map.getAllUniqueUsers()) {
            User user = map.getUser(uuid);
            String address = user.getLastLoginAddress();
            String uuidString = uuid.toString();

            if (!address.isEmpty()) {
                List<String> accounts = offlineList.getOrDefault(address, new ArrayList<>());

                if (!accounts.contains(address)) {
                    accounts.add(uuidString);
                    offlineList.put(address, accounts);
                }
            }
        }

        MessageUtils.message(sender, "&6&l» &7Import complete");
    }

}
