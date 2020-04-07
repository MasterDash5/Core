package dashnetwork.core.command.commands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CommandSkin extends CoreCommand {

    public CommandSkin() {
        super("skin", PermissionType.ADMIN, false);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (args.length > 1) {
            List<Player> targets = new ArrayList<>();

            if (args.length > 0) {
                List<Player> selector = SelectorUtils.getPlayers(sender, args[0]);

                if (selector != null)
                    targets = selector;
            }

            targets.removeIf(target -> !SenderUtils.canSee(sender, target));

            if (targets.isEmpty())
                MessageUtils.usage(sender, label, "<player> <skin>");
            else {
                String arg = args[1];
                String uuid = "";
                String texture = "";
                String signature = "";

                try {
                    uuid = UUID.fromString(arg).toString().replace("-", "");
                } catch (IllegalArgumentException invalid) {
                    try {
                        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + arg);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                        JsonElement json = new JsonParser().parse(reader);
                        uuid = json.getAsJsonObject().get("id").getAsString();

                        reader.close();
                    } catch (IllegalStateException illegal) {
                        MessageUtils.message(sender, "&6&l» &7No account with the name &6" + arg);
                    } catch (FileNotFoundException file) {}
                    catch (Exception exception) {
                        MessageUtils.error(sender, exception);
                        exception.printStackTrace();
                    }
                }

                try {
                    URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
                    InputStreamReader reader = new InputStreamReader(url.openStream());
                    JsonObject textureProperty = new JsonParser().parse(reader).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();

                    texture = textureProperty.get("value").getAsString();
                    signature = textureProperty.get("signature").getAsString();

                    reader.close();
                } catch (Exception exception) {
                    MessageUtils.message(sender, "&6&l» &7Failed to read mojang's session servers");
                }

                for (Player target : targets) {
                    int ping = target.spigot().getPing();
                    EnumWrappers.NativeGameMode gamemode = EnumWrappers.NativeGameMode.fromBukkit(target.getGameMode());
                    WrappedChatComponent tabname = WrappedChatComponent.fromText(target.getPlayerListName());

                    EntityPlayer entityplayer = ((CraftPlayer) target).getHandle();
                    GameProfile profile = entityplayer.getProfile();
                    PropertyMap properties = profile.getProperties();

                    PacketContainer clear = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
                    PlayerInfoData clearData = new PlayerInfoData(WrappedGameProfile.fromHandle(profile), ping, gamemode, tabname);

                    clear.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
                    clear.getPlayerInfoDataLists().write(0, Arrays.asList(clearData));

                    properties.clear();
                    properties.put("textures", new Property("textures", texture, signature));

                    PacketContainer add = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
                    PlayerInfoData addData = new PlayerInfoData(WrappedGameProfile.fromHandle(profile), ping, gamemode, tabname);

                    add.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
                    add.getPlayerInfoDataLists().write(0, Arrays.asList(addData));

                    new BukkitRunnable() {
                        public void run() {
                            for (Player online : Bukkit.getOnlinePlayers()) {
                                if (online.canSee(target)) {
                                    online.hidePlayer(plugin, target);
                                    online.showPlayer(plugin, target);
                                }
                            }

                            try {
                                ProtocolManager manager = ProtocolLibrary.getProtocolManager();
                                manager.sendServerPacket(target, clear);
                                manager.sendServerPacket(target, add);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }

                            entityplayer.server.getPlayerList().moveToWorld(entityplayer, entityplayer.dimension, false, target.getLocation(), true);
                        }
                    }.runTaskLater(plugin, 1);
                }
            }
        } else
            MessageUtils.usage(sender, label, "<player> <skin>");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length <= 2)
            return null;
        return new ArrayList<>();
    }

}
