package dashnetwork.core.command.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.CommandSender;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.util.*;

public class CommandNamemc extends CoreCommand {

    public CommandNamemc() {
        super("namemc", PermissionType.NONE, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (args.length > 0) {
            MessageUtils.message(sender, "&6&l» &7Retrieving username history...");

            String arg = args[0];
            String uuid = "";

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
                }
            }

            try {
                URL url1 = new URL("https://api.mojang.com/user/profiles/" + uuid + "/names");
                BufferedReader reader1 = new BufferedReader(new InputStreamReader(url1.openStream()));
                JsonElement json1 = new JsonParser().parse(reader1);
                List<String> names = new ArrayList<>();
                List<String> dates = new ArrayList<>();

                for (JsonElement element : json1.getAsJsonArray()) {
                    JsonObject object = element.getAsJsonObject();
                    String name = object.get("name").getAsString();

                    if (object.has("changedToAt")) {
                        Date date = new Date(object.get("changedToAt").getAsLong());
                        String formatted = DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);

                        dates.add(formatted);
                    } else
                        dates.add("???");

                    names.add(name);
                }

                reader1.close();

                URL url2 = new URL("https://api.namemc.com/profile/" + uuid + "/friends");
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(url2.openStream()));
                JsonElement json2 = new JsonParser().parse(reader2);
                List<String> friends = new ArrayList<>();

                for (JsonElement element : json2.getAsJsonArray()) {
                    friends.add(element.getAsJsonObject().get("name").getAsString());

                    if (friends.size() >= 32)
                        break;
                }

                reader2.close();

                BufferedImage head = ImageIO.read(new URL("https://crafatar.com/avatars/" + uuid + "?overlay"));
                String[] headText = new ImageMessage(head, 8, '⬛').getLines();

                int namesLength = names.size();
                int headLength = headText.length;
                int length = namesLength + 2 > headLength ? namesLength + 2 : headLength;

                String text = "";

                for (int i = 0; i < length; i++) {
                    int namesIndex = i - 2;

                    if (i < headLength)
                        text += headText[i] + "    ";
                    else
                        text += "                    ";

                    if (i == 0)
                        text += "&6&lUsername History";

                    if (i > 1 && namesIndex < namesLength)
                        text += "&7" + names.get(namesIndex) + " &6-&7 " + dates.get(namesIndex);

                    if (i < length - 1)
                        text += "\n";
                }

                MessageBuilder message = new MessageBuilder();
                message.append(text).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&7Registered Friends: &6" + ListUtils.fromList(friends, false, false)).clickEvent(ClickEvent.Action.OPEN_URL, "https://namemc.com/profile/" + uuid);

                MessageUtils.message(sender, message.build());
            } catch (IllegalStateException illegal) {
                MessageUtils.message(sender, "&6&l» &7No account with the uuid &6" + arg);
            } catch (FileNotFoundException file) {}
            catch (Exception exception) {
                MessageUtils.error(sender, exception);
            }
        }
    }

    @Override
    public CommandNode onTabComplete(LiteralArgumentBuilder builder) {
        return builder.then(Arguments.stringTypeWord("username/uuid")).build();
    }

}
