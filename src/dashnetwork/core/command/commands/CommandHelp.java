package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.creative.Creative;
import dashnetwork.core.survival.Survival;
import dashnetwork.core.utils.LazyUtils;
import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHelp extends CoreCommand {

    public CommandHelp() {
        super("help", PermissionType.NONE, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            World world = player.getWorld();
            String name = world.getName();
            MessageBuilder message = new MessageBuilder();

            if (world.equals(Creative.getWorld())) {
                message.append("&6&l» &6&m     &7 Creative Help &6&m     \n");
                message.append("\n");
                message.append("&6&l» &7Plot commands\n");
                message.append("&6- &7Use &6/plot auto &7to claim up to &64 &7new plots\n").clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/plot auto");
                message.append("&6- &7Use &6/plot home &7to get back to your plot\n").clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/plot home");
                message.append("&6- &7Use &6/plot add &7-or- &6/plot trust &7to add someone to your plot\n").clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/plot trust");
                message.append("&6- &7Use &6/plot visit &7to teleport to someone else's plot\n").clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/plot visit");
                message.append("\n");
                message.append("&6&l» &7Building commands\n");
                message.append("&6- &7Use &6/debugstick &7to get a debug stick\n").clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/debugstick");
                message.append("&6- &7Use &6//wand &7to get a WorldEdit wand\n").clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "//wand");
                message.append("&6- &7All WorldEdit commands: &6https://worldedit.enginehub.org/en/latest/commands/").clickEvent(ClickEvent.Action.OPEN_URL, "https://worldedit.enginehub.org/en/latest/commands/");
            } else if (LazyUtils.anyEquals(world, Survival.getWorld(), Survival.getNether(), Survival.getEnd())) {
                message.append("&6&l» &6&m     &7 Survival Help &6&m     \n");
                message.append("\n");
                message.append("&6&l» &7Land Claiming\n");
                message.append("&6- &7\n");
                message.append("&6- &7\n");
                message.append("&6- &7\n");
                message.append("\n");
                message.append("&6&l» &7Economy commands\n");
                message.append("&6- &7\n");
                message.append("&6- &7\n");
                message.append("&6- &7");
            } else if (name.equals("KitPvP")) { // TODO: "KitPvP.getWorld()" API in KitPvP 4.0
                message.append("&6&l» &6&m     &7 PvP Help &6&m     \n");
                message.append("\n");
                message.append("&6&l» &7Game Modes\n");
                message.append("&6- &7Right click a &6&lKit Selector &7sign for free-for-all combat\n");
                message.append("&6- &7Right click the &6&lDuels &7npc to queue for a 1v1 duel\n");
                message.append("&6- &7Right click the &6&lBot Battles &7npc to battle with a bot\n");
                message.append("\n");
                message.append("&6&l» &7Available Kits");
                message.append("&6- Basic, Archer, Speed, Tank, Pyro");
            } else if (name.equals("skyworld")) {
                message.append("&6&l» &6&m     &7 Skyblock Help &6&m     \n");
                message.append("\n");
                message.append("&6&l» &7Island commands\n");
                message.append("&6- &7Use &6/is &7to access the Skyblock menu\n");
                message.append("&6- &7Use &6/is home &7to get back to your island\n");
                message.append("&6- &7Use &6/is invite &7to add someone to your island\n");
                message.append("\n");
                message.append("&6&l» &7Economy commands\n");
                message.append("&6- &7Use &6/shop &7to access the Skyblock shop\n");
                message.append("&6- &7Use &6/sell &7to bulk sell items");
            }

            MessageUtils.message(player, message.build());
        } else
            MessageUtils.playerCommandOnly();
    }

}
