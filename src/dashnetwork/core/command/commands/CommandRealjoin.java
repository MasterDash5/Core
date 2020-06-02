package dashnetwork.core.command.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import protocolsupport.api.ProtocolSupportAPI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

public class CommandRealjoin extends CoreCommand {

    public CommandRealjoin() {
        super("realjoin", PermissionType.OWNER, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                String arg = args[0];
                String username;
                String uuid;
                String texture;
                String signature;

                try {
                    uuid = UUID.fromString(arg).toString().replace("-", "");
                } catch (IllegalArgumentException invalid) {
                    try {
                        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + arg);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                        JsonElement json = new JsonParser().parse(reader);

                        uuid = json.getAsJsonObject().get("id").getAsString();

                        reader.close();
                    } catch (Exception exception) {
                        MessageUtils.error(sender, exception);
                        return;
                    }
                }

                try {
                    URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                    JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
                    JsonObject property = json.get("properties").getAsJsonArray().get(0).getAsJsonObject();

                    username = json.get("name").getAsString();
                    texture = property.get("value").getAsString();
                    signature = property.get("signature").getAsString();
                } catch (Exception exception) {
                    MessageUtils.error(sender, exception);
                    exception.printStackTrace();
                    return;
                }

                GameProfile profile = new GameProfile(UUID.fromString(
                        uuid.replaceFirst(
                                "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"
                        )
                ), username);
                profile.getProperties().put("textures", new Property("textures", texture, signature));

                DataUtils.getQueuedRealjoins().put(player.getUniqueId(), profile);

                MessageUtils.message(sender, "&6&l» &7You will be &6" + username + "&7 on the next relog");
            } else
                MessageUtils.usage(sender, label, "<player>");
        } else
            MessageUtils.playerCommandOnly();
    }

    @Override
    public CommandNode onTabComplete(LiteralArgumentBuilder builder) {
        return builder.then(Arguments.stringTypeWord("username/uuid")).build();
    }

}
