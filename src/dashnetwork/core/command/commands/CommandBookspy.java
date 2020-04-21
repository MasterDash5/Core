package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.global.listeners.EditBookListener;
import dashnetwork.core.utils.*;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class CommandBookspy extends CoreCommand {

    public CommandBookspy() {
        super("bookspy", PermissionType.STAFF, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        List<Player> targets = new ArrayList<>();
        Player player = sender instanceof Player ? (Player) sender : null;

        if (args.length > 0) {
            String arg = args[0];

            if (SenderUtils.isOwner(sender)) {
                List<Player> selector = SelectorUtils.getPlayers(sender, arg);

                if (selector != null)
                    targets = selector;
            }

            try {
                Integer id = Integer.valueOf(arg);
                BookMeta book = EditBookListener.getBooks().get(id);

                if (book != null) {
                    ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
                    item.setItemMeta(book);

                    player.getInventory().addItem(item);
                } else
                    MessageUtils.message(sender, "&6&l» &7That book has been cleared");

                return;
            } catch (Exception exception) {}
        } else if (player != null)
            targets.add(player);

        targets.removeIf(target -> !SenderUtils.canSee(sender, target));

        if (targets.isEmpty())
            MessageUtils.usage(sender, label, "<player>");
        else {
            for (Player target : targets) {
                User user = User.getUser(target);
                boolean inBookSpy = !user.inBookSpy();

                user.setInBookSpy(inBookSpy);

                if (inBookSpy)
                    MessageUtils.message(target, "&6&l» &7You are now in BookSpy");
                else
                    MessageUtils.message(target, "&6&l» &7You are no longer in BookSpy");
            }

            if (player == null || ListUtils.containsOtherThan(targets, player)) {
                targets.remove(player);

                String displaynames = ListUtils.fromList(NameUtils.toDisplayNames(targets), false, false);
                String names = ListUtils.fromList(NameUtils.toNames(targets), false, false);

                MessageBuilder message = new MessageBuilder();
                message.append("&6&l» ");
                message.append("&6" + displaynames).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + names);
                message.append("&7 toggled BookSpy");

                MessageUtils.message(sender, message.build());
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1 && SenderUtils.isOwner(sender))
            return null;
        return new ArrayList<>();
    }

}
