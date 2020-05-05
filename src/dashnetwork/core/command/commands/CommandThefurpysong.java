package dashnetwork.core.command.commands;

import dashnetwork.core.command.CoreCommand;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import org.bukkit.command.CommandSender;

public class CommandThefurpysong extends CoreCommand {

    public CommandThefurpysong() {
        super("thefurpysong", PermissionType.NONE, true);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        MessageUtils.message(sender, "&7The Furpy Song: by Skullrama's Crazy Friend");
        MessageUtils.message(sender, " ");
        MessageUtils.message(sender, "&7(Theme from Ikuroshitsuji closing season 1)");
        MessageUtils.message(sender, " ");
        MessageUtils.message(sender, "&6One day at my lunch, Skull told me 'bout a bunch.");
        MessageUtils.message(sender, "&6Then days after that, my life changed forever.");
        MessageUtils.message(sender, " ");
        MessageUtils.message(sender, "&6But now I won't waste any time, cause something is on my mind.");
        MessageUtils.message(sender, "&6Someone that is awesome, a 54 kid.");
        MessageUtils.message(sender, " ");
        MessageUtils.message(sender, "&6Fur- PEE, Fur- PEE, oh yeah, Furpy my one and only, reach for the ceiling.");
        MessageUtils.message(sender, "&6Fur- PEE A Fur and a Pee.");
        MessageUtils.message(sender, "&6My one, and only Furpy.");
        MessageUtils.message(sender, " ");
        MessageUtils.message(sender, "&6It doesn't matter who you are, Even if you are married and have kids, Root Beer and Panda Wilson.");
        MessageUtils.message(sender, "&6X + 1 does that matter?");
        MessageUtils.message(sender, " ");
        MessageUtils.message(sender, "&6All my friends they ran away, but you just come back and stay.");
        MessageUtils.message(sender, "&6This love song to you.");
        MessageUtils.message(sender, "&6Yeah, you 54 kid.");
        MessageUtils.message(sender, " ");
        MessageUtils.message(sender, "&6Fur- PEE, fur- PEE, oh yeah.");
        MessageUtils.message(sender, "&6Please don't be a girl I'd be depressed.");
        MessageUtils.message(sender, "&6Reach for the ceiling My Furpy.");
        MessageUtils.message(sender, " ");
        MessageUtils.message(sender, "&6My one and only Furpy, a Fur and a Pee, you are...");
        MessageUtils.message(sender, "&6my only...");
        MessageUtils.message(sender, "&6you are");
        MessageUtils.message(sender, "&6my true");
        MessageUtils.message(sender, "&6FURPY!!");
        MessageUtils.message(sender, " ");
        MessageUtils.message(sender, "&7(Applause)");
    }

}
