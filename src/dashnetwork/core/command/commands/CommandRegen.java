package dashnetwork.core.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class CommandRegen extends CoreCommand {

    public CommandRegen() {
        super("regen", PermissionType.ADMIN, false);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        List<Player> targets = new ArrayList<>();
        Player player = sender instanceof Player ? (Player) sender : null;

        if (args.length > 0 && SenderUtils.isOwner(sender)) {
            List<Player> selector = SelectorUtils.getPlayers(sender, args[0]);

            if (selector != null)
                targets = selector;
        } else if (player != null)
            targets.add(player);

        targets.removeIf(target -> !SenderUtils.canSee(sender, target));

        if (targets.isEmpty())
            MessageUtils.usage(sender, label, "<player>");
        else {
            for (Player target : targets) {
                boolean hasRegeneration = target.hasPotionEffect(PotionEffectType.REGENERATION);
                boolean hasResistance = target.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                boolean hasSaturation = target.hasPotionEffect(PotionEffectType.SATURATION);

                if (hasRegeneration || hasResistance || hasSaturation) {
                    target.removePotionEffect(PotionEffectType.REGENERATION);
                    target.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                    target.removePotionEffect(PotionEffectType.SATURATION);

                    MessageUtils.message(target, "&6&l» &7You no longer have Regen");
                } else {
                    PotionEffect regeneration = new PotionEffect(PotionEffectType.REGENERATION, 1000000, 127, true, false, false);
                    PotionEffect resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 127, true, false, false);
                    PotionEffect saturation = new PotionEffect(PotionEffectType.SATURATION, 1000000, 127, true, false, false);

                    target.addPotionEffect(regeneration);
                    target.addPotionEffect(resistance);
                    target.addPotionEffect(saturation);

                    MessageUtils.message(target, "&6&l» &7You now have Regen");
                }
            }

            if (player == null || ListUtils.containsOtherThan(targets, player)) {
                targets.remove(player);

                String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(targets), false, false);
                String names = ListUtils.fromList(NameUtils.toNames(targets), false, false);

                MessageBuilder message = new MessageBuilder();
                message.append("&6&l» ");
                message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
                message.append("&7 toggled Regen");

                MessageUtils.message(sender, message.build());
            }
        }
    }

    @Override
    public CommandNode onTabComplete(LiteralArgumentBuilder builder) {
        return builder.then(Arguments.playersType("targets")).build();
    }

}
