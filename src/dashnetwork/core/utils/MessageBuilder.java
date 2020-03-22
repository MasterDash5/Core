package dashnetwork.core.utils;

import net.md_5.bungee.api.chat.*;

import java.util.HashMap;
import java.util.Map;

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

        hoverEvents.put(line, new HoverEvent(action, new BaseComponent[] { new TextComponent(MessageUtils.color(string)) }));
        return this;
    }

    public boolean isEmpty() {
        return lines.size() <= 0;
    }

    public TextComponent[] build() { // TODO: Fix colors with new lines
        int size = lines.size();
        TextComponent[] components = new TextComponent[size];

        for (int i = 0; i < size; i++) {
            if (i > 0)
                components[i - 1].addExtra("\n");

            components[i] = new TextComponent(new ComponentBuilder(lines.get(i)).create());
            ClickEvent clickEvent = clickEvents.get(i);
            HoverEvent hoverEvent = hoverEvents.get(i);

            if (clickEvent != null)
                components[i].setClickEvent(clickEvent);

            if (hoverEvent != null)
                components[i].setHoverEvent(hoverEvent);
        }

        return components;
    }

}
