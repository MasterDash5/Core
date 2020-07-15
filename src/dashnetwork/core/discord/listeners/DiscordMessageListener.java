package dashnetwork.core.discord.listeners;

import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.core.utils.MessageUtils;
import dashnetwork.core.utils.PermissionType;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DiscordGuildMessagePreProcessEvent;
import github.scarsz.discordsrv.dependencies.jda.api.entities.*;
import github.scarsz.discordsrv.util.DiscordUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

import java.util.List;

public class DiscordMessageListener {

    @Subscribe
    public void onDiscordChat(DiscordGuildMessagePreProcessEvent event) {
        DiscordSRV discord = DiscordSRV.getPlugin();
        TextChannel channel = event.getChannel();

        if (channel.equals(discord.getDestinationTextChannelForGameChannelName("staffchat"))) {
            User user = event.getAuthor();
            Member member = event.getMember();
            Role role = DiscordUtil.getTopRole(member);
            Message message = event.getMessage();
            String nickname = member.getNickname();
            String name = nickname == null ? event.getAuthor().getName() : nickname;
            String tag = user.getAsTag();
            String display = message.getContentDisplay();
            List<Message.Attachment> attachments = message.getAttachments();
            boolean hasAttachment = attachments.size() > 0;
            String rank = "&2&lPlayer";

            if (role != null)
                rank = DiscordUtil.convertRoleToMinecraftColor(role) + "&l" + role.getName().split(" ")[0];

            if (display.isEmpty())
                display = "&oClick to view attachment";

            MessageBuilder builder = new MessageBuilder();
            builder.append("&9&lStaff ");
            builder.append("&6" + rank + "&6 " + name).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + tag);

            if (hasAttachment)
                builder.append("&6&l > &6" + display).clickEvent(ClickEvent.Action.OPEN_URL, attachments.get(0).getUrl());
            else
                builder.append("&6&l > &6" + display);

            MessageUtils.broadcast(true, null, PermissionType.STAFF, builder.build());

            event.setCancelled(true);
        }
    }

}
