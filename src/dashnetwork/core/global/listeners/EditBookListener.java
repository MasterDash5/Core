package dashnetwork.core.global.listeners;

import dashnetwork.core.Core;
import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.User;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class EditBookListener implements Listener {

    private static Map<Integer, BookMeta> books = new HashMap<>();

    public static Map<Integer, BookMeta> getBooks() {
        return books;
    }

    @EventHandler
    public void onPlayerEditBook(PlayerEditBookEvent event) {
        Player player = event.getPlayer();
        BookMeta meta = event.getNewBookMeta();
        int available = 0;

        while (books.containsKey(available))
            available++;

        final int id = available;

        books.put(id, meta);

        new BukkitRunnable() {
            public void run() {
                books.remove(id);
            }
        }.runTaskLaterAsynchronously(Core.getInstance(), 1200);

        String command = "/bookspy " + id;
        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» ").clickEvent(ClickEvent.Action.RUN_COMMAND, command);
        message.append("&6" + player.getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + player.getName()).clickEvent(ClickEvent.Action.RUN_COMMAND, command);
        message.append(" &7edited book. Click to receive copy.").clickEvent(ClickEvent.Action.RUN_COMMAND, command);

        for (User user : User.getUsers())
            if (user.inBookSpy())
                MessageUtils.message(user, message.build());
    }

}
