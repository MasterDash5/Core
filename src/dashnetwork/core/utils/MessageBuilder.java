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

        hoverEvents.put(line, new HoverEvent(action, new BaseComponent[] { new TextComponent(MessageUtils.color(string)) }));
        return this;
    }

    public boolean isEmpty() {
        return lines.size() <= 0;
    }

    public TextComponent[] build() {
        List<TextComponent> components = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            ClickEvent clickEvent = clickEvents.get(i);
            HoverEvent hoverEvent = hoverEvents.get(i);
            List<TextComponent> translated = new ArrayList<>();
            String[] array = line.split("§");

            TextComponent temp = new TextComponent();

            if (clickEvent != null)
                temp.setClickEvent(clickEvent);

            if (hoverEvent != null)
                temp.setHoverEvent(hoverEvent);

            for (int j = 0; j < array.length; j++) {
                String split = array[i];
                ChatColor color = ChatColor.WHITE;

                if (split.isEmpty())
                    continue;

                if (j > 0) {
                    if (split.length() > 1) {
                        color = ChatColor.getByChar(split.substring(0, 1));
                        split = split.substring(1);
                    } else {
                        color = ChatColor.getByChar(split);
                        split = "";
                    }
                }

                if (color.equals(ChatColor.RESET))
                    color = ChatColor.WHITE;

                if (!temp.getText().isEmpty()) {
                    translated.add(temp);
                    temp = new TextComponent();

                    if (clickEvent != null)
                        temp.setClickEvent(clickEvent);

                    if (hoverEvent != null)
                        temp.setHoverEvent(hoverEvent);
                }

                temp.setText(split);

                if (color.isColor()) {
                    if (temp.getColorRaw() != null) {
                        translated.add(temp);
                        temp = new TextComponent();

                        if (clickEvent != null)
                            temp.setClickEvent(clickEvent);

                        if (hoverEvent != null)
                            temp.setHoverEvent(hoverEvent);
                    }

                    temp.setColor(color.asBungee());
                } else {
                    final TextComponent last = temp;
                    translated.add(temp);

                    temp = new TextComponent();
                    temp.setBold(last.isBold());
                    temp.setObfuscated(last.isObfuscated());
                    temp.setItalic(last.isItalic());
                    temp.setUnderlined(last.isUnderlined());
                    temp.setStrikethrough(last.isStrikethrough());

                    if (clickEvent != null)
                        temp.setClickEvent(clickEvent);

                    if (hoverEvent != null)
                        temp.setHoverEvent(hoverEvent);

                    switch (color) {
                        case BOLD:
                            temp.setBold(true);
                        case MAGIC:
                            temp.setObfuscated(true);
                        case ITALIC:
                            temp.setItalic(true);
                        case UNDERLINE:
                            temp.setUnderlined(true);
                        case STRIKETHROUGH:
                            temp.setStrikethrough(true);
                    }
                }
            }

            temp.addExtra("\n");

            translated.add(temp);
            components.addAll(translated);
        }

        return components.toArray(new TextComponent[components.size()]);
    }

    @Deprecated
    public TextComponent[] buildOld() { // TODO: Fix colors with new lines
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
