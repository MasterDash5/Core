package dashnetwork.core.discord.listeners;

import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DiscordGuildMessagePreProcessEvent;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Member;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Role;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.dependencies.jda.api.entities.User;
import github.scarsz.discordsrv.util.DiscordUtil;
import net.md_5.bungee.api.chat.HoverEvent;

public class DiscordMessageListener {

    @Subscribe
    public void onDiscordChat(DiscordGuildMessagePreProcessEvent event) {
        DiscordSRV discord = DiscordSRV.getPlugin();
        TextChannel channel = event.getChannel();
        User user = event.getAuthor();
        Member member = event.getMember();
        Role role = DiscordUtil.getTopRole(member);
        String nickname = member.getNickname();
        String name = nickname == null ? event.getAuthor().getName() : nickname;
        String tag = user.getAsTag();
        String display = event.getMessage().getContentDisplay();

        if (channel.equals(discord.getDestinationTextChannelForGameChannelName("staffchat"))) {
            String rank = "&2&lPlayer";

            if (role != null)
                rank = DiscordUtil.convertRoleToMinecraftColor(role) + "&l" + role.getName().split(" ")[0];

            MessageBuilder message = new MessageBuilder();
            message.append("&9&lStaff ");
            message.append("&6" + rank + "&6 " + name).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + tag);
            message.append("&6&l > &6" + display);

            MessageUtils.broadcast(true, null, PermissionType.STAFF, message.build());
        }
    }

}
