package com.lazrproductions.lazrslib.common.component;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * A class containing several utilities for {@link Component}
 */
public class ComponentUtilities {
    /**
     * Separate the given list of components at each space.
     * @param list The list of components to split
     * @return The list of words.
     */
    public static List<Component> divideIntoWords(List<Component> list) {
        ArrayList<Component> newList = new ArrayList<>();

        for (Component component : list) {
            String[] l = component.getString().split(" ");
            Style style = component.getStyle();
            for (String s : l) newList.add(Component.literal(s).withStyle(style));
        }

        return newList;
    }

    /**
     * Separate the given list of components at each space.
     * @param component The component to split.
     * @return The list of words.
     */
    public static List<Component> divideIntoWords(Component component) {
        ArrayList<Component> newList = new ArrayList<>();

        String[] l = component.getString().split(" ");
        Style style = component.getStyle();
        for (String s : l) newList.add(Component.literal(s).withStyle(style));


        return newList;
    }

    /**
     * Get the total height of the given list of components when wrapping around the given width.
     * @param instance The instance of Minecraft to get the font from.
     * @param list The list of components to get the height of, each element is on a new line.
     * @param width The width to wrap the components around.
     * @return The total height in pixels.
     */
    public static int getTotalHeight(@Nonnull Minecraft instance, List<Component> list, int width) {
        return getTotalHeight(instance.font, list, width);
    }

    /**
     * Get the total height of the given list of components when wrapping around the given width.
     * @param font The font to use.
     * @param list The list of components to get the height of, each element is on a new line.
     * @param width The width to wrap the components around.
     * @return The total height in pixels.
     */
    public static int getTotalHeight(@Nonnull Font font, List<Component> list, int width) {
        int totalHeight = 0;
        for (Component component : list) totalHeight += font.wordWrapHeight(component, width);
        return totalHeight;
    }

    /**
     * Get the total height of the given list of components when wrapping around the given width.
     * @param list The list of components to get the height of, each element is on a new line.
     * @param width The width to wrap the components around.
     * @return The total height in pixels.
     */
    public static int getTotalHeight(List<Component> list, int width) {
        return getTotalHeight(Minecraft.getInstance(), list, width);
    }

    /**
     * Perform a <code>String.substring</code> on the parsed Component of the given JSON string, ending at the given endIndex.
     * @param json The JSON to parse, then substring.
     * @param endIndex The ending index of the substring, exclusive.
     * @return The substring Component parsed from the given JSON.
     */
    @Nonnull
    public static Component substringJson(String json, int endIndex) {
        try {
            Component comp = Component.Serializer.fromJson(json);
            if (comp != null) {
                List<Component> list = comp.toFlatList();

                MutableComponent result = Component.literal("");

                int currentIndex = 0;
                for (Component component : list) {
                    for (int letter = 0; letter < component.getString().length(); letter++) {
                        if (currentIndex < endIndex)
                            result.append(Component.literal(component.getString().substring(letter, letter + 1))
                                    .withStyle(component.getStyle()));
                        currentIndex++;
                    }
                }
                return result;
            }
        } catch (Exception e) {
            return Component.literal(json.substring(0, endIndex));
        }
        return Component.literal("null");
    }

    /**
     * Parse the given string from JSON to a Component.
     * @param json The JSON string to parse.
     * @return The parsed Component
     */
    @Nonnull
    public static Component parseFromJson(String json) {
        try {
            Component comp = Component.Serializer.fromJson(json);
            if (comp != null)
                return comp;
            return Component.literal("null");
        } catch (Exception e) {
            return Component.literal(json);
        }
    }
}
