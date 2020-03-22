package dashnetwork.core.utils;

import net.md_5.bungee.api.chat.*;
import org.bukkit.ChatColor;

import java.util.*;

public class MessageBuilder {

    private Map<Integer, String> lines;
    private Map<Integer, ClickEvent> clickEvents;
    private Map<Integer, HoverEvent> hoverEvents;

    public MessageBuilder() {
        lines = new HashMap<>();
        clickEvents = new HashMap<>();
        hoverEvents = new HashMap<>();
    }

    public MessageBuilder addLine(String message) {
        lines.put(lines.size(), MessageUtils.color(message));
        return this;
    }

    public MessageBuilder addClickEvent(ClickEvent.Action action, String string) {
        int line = lines.size() - 1;

        if (hoverEvents.containsKey(line))
            string = TextComponent.toLegacyText(hoverEvents.get(line).getValue()) + "\n" + string;

        clickEvents.put(line, new ClickEvent(action, string));
        return this;
    }

    public MessageBuilder addHoverEvent(HoverEvent.Action action, String string) {
        int line = lines.size() - 1;

        if (hoverEvents.containsKey(line))
            string = TextComponent.toLegacyText(hoverEvents.get(line).getValue()) + "\n" + string;

        hoverEvents.put(line, new HoverEvent(action, TextComponent.fromLegacyText(string)));
        return this;
    }

    public boolean isEmpty() {
        return lines.size() <= 0;
    }

    public BaseComponent[] build() {
        List<BaseComponent> components = new ArrayList<>();
        int length = lines.size();

        for (int i = 0; i < length; i++) {
            String line = lines.get(i);
            ClickEvent clickEvent = clickEvents.get(i);
            HoverEvent hoverEvent = hoverEvents.get(i);
            List<BaseComponent> current = new ArrayList<>();

            if (i < length - 1)
                line += "\n";

            for (BaseComponent component : TextComponent.fromLegacyText(line)) {
                component.setClickEvent(clickEvent);
                component.setHoverEvent(hoverEvent);
                current.add(component);
            }

            components.addAll(current);
        }

        return components.toArray(new BaseComponent[components.size()]);
    }

}
