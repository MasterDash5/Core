package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandMoonphase extends CoreCommand {

    public CommandMoonphase() {
        super("moonphase", PermissionType.ADMIN, false);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                Phase phase = Phase.fromReference(args[0]);

                if (phase == null)
                    MessageUtils.message(sender, "&6&l» &7Invalid moon phase");
                else {
                    World world = player.getWorld();
                    long time = world.getFullTime();

                    while (time - 24000 > 0)
                        time -= 24000;

                    time += phase.timeToAdd;

                    world.setFullTime(time);

                    MessageUtils.message(player, "&6&l» &7Moon phase set to &6" + GrammarUtils.capitalization(phase.name().replace("_", " ")));
                }
            } else
                MessageUtils.usage(sender, label, "<phase>");
        } else
            MessageUtils.playerCommandOnly();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1)
            for (Phase phase : Phase.values())
                for (String reference : phase.references)
                    if (StringUtils.startsWithIgnoreCase(reference, args[0]))
                        completions.add(reference);

        return completions;
    }

    private enum Phase {
        FULL_MOON(0L, "full", "fullmoon"),
        WANING_GIBBOUS(24000L, "waning", "waninggibbous"),
        LAST_QUARTER(48000L, "half", "last", "quarter", "lastquarter"),
        WANING_CRESCENT(72000L, "crescent", "waningcrescent"),
        NEW_MOON(96000L, "new", "newmoon"),
        WAXING_CRESCENT(120000, "waxingcrescent"),
        FIRST_QUARTER(144000, "first", "firstquarter"),
        WAXING_GIBBOUS(168000L, "waxing", "waxinggibbous");

        private long timeToAdd;
        private String[] references;

        Phase(long timeToAdd, String... references) {
            this.timeToAdd = timeToAdd;
            this.references = references;
        }

        private static Phase fromReference(String input) {
            for (Phase phase : values())
                for (String reference : phase.references)
                    if (reference.equalsIgnoreCase(input))
                        return phase;
            return null;
        }
    }

}
