package dashnetwork.core.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.sun.management.OperatingSystemMXBean;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CommandServerinfo extends CoreCommand {

    private static OperatingSystemMXBean bean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
    private static DecimalFormat formatter = new DecimalFormat("#######.##");

    public CommandServerinfo() {
        super("serverinfo", PermissionType.ADMIN, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player && args.length > 0) {
            List<Player> targets = new ArrayList<>();
            Player player = (Player) sender;

            if (args.length > 1 && SenderUtils.isOwner(sender)) {
                List<Player> selector = SelectorUtils.getPlayers(sender, args[1]);

                if (selector != null)
                    targets = selector;
            } else
                targets.add(player);

            targets.removeIf(target -> !SenderUtils.canSee(sender, target));

            if (targets.isEmpty())
                MessageUtils.usage(sender, label, "&6&l» &7No players found");
            else {
                for (Player target : targets) {
                    User user = User.getUser(target);
                    boolean inServerInfo = !user.inServerInfo();

                    user.setInServerInfo(inServerInfo);

                    if (inServerInfo)
                        MessageUtils.message(target, "&6&l» &7You are now in ServerInfo");
                    else
                        MessageUtils.message(target, "&6&l» &7You are no longer in ServerInfo");
                }

                if (player == null || ListUtils.containsOtherThan(targets, player)) {
                    targets.remove(player);

                    String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(targets), false, false);
                    String names = ListUtils.fromList(NameUtils.toNames(targets), false, false);

                    MessageBuilder message = new MessageBuilder();
                    message.append("&6&l» ");
                    message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
                    message.append("&7 toggled ServerInfo");

                    MessageUtils.message(sender, message.build());
                }
            }
        } else {
            Runtime runtime = Runtime.getRuntime();

            int cores = bean.getAvailableProcessors();

            double totalLoad = bean.getSystemCpuLoad();
            double serverLoad = bean.getProcessCpuLoad();
            String totalUsage = StringUtils.shortenNumber(totalLoad * 100, 2) + "%";
            String serverUsage = StringUtils.shortenNumber(serverLoad * 100, 2) + "%";

            if (totalLoad == 0)
                totalUsage = "Idle";
            else if (totalLoad == -1)
                totalUsage = "Unavailable";

            if (serverLoad == 0)
                serverUsage = "Idle";
            else if (serverLoad == -1)
                serverUsage = "Unavailable";

            int chunks = 0;
            int entities = 0;
            int tiles = 0;

            for (World world : Bukkit.getWorlds()) {
                chunks += world.getLoadedChunks().length;
                entities += world.getEntityCount();
                tiles += world.getTickableTileEntityCount();
            }

            double tps = TpsUtils.getTPS();

            double memory = runtime.maxMemory() / 1024/ 1024;
            double allocated = runtime.totalMemory() / 1024 / 1024;
            double free = runtime.freeMemory() / 1024 / 1024;
            double used = allocated - free;

            String memoryFormat = formatter.format(memory);
            String allocatedFormat = formatter.format(allocated);
            String freeFormat = formatter.format(free);
            String usedFormat = formatter.format(used);

            MessageBuilder message = new MessageBuilder();
            message.append("&6&l» &7TPS: &6" + tps);
            message.append("\n&6&l» &7Memory: &6" + usedFormat + "&7/&6" + allocatedFormat + " &7MB (&6" + memoryFormat + "&7 MB max)").hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + freeFormat + " &7MB free");
            message.append("\n&6&l» &7Total CPU Usage: &6" + totalUsage + "&7 Server CPU Usage: &6" + serverUsage).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&7CPU Cores: &6" + cores);
            message.append("\n&6&l» &7Loaded Chunks: &6" + chunks + "&7 Entiies: &6" + entities + "&7 Tiles: &6" + tiles);

            MessageUtils.message(sender, message.build());
        }
    }

    @Override
    public CommandNode onTabComplete(LiteralArgumentBuilder builder) {
        return builder.then(Arguments.literal("actionbar")).build();
    }

}
