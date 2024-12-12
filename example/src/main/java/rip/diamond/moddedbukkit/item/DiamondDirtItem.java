package rip.diamond.moddedbukkit.item;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import rip.diamond.moddedbukkit.ExamplePlugin;

import java.io.InputStream;
import java.util.List;

public class DiamondDirtItem implements ModdedPlaceableItem {

    public static Key KEY = Key.key("example:diamond_dirt");

    @Override
    public Key getKey() {
        return KEY;
    }

    @Override
    public Key getTextureKey() {
        return Key.key("example:item/diamond_dirt");
    }

    @Override
    public InputStream getTextureResource() {
        return ExamplePlugin.INSTANCE.getResource("pack/textures/block/diamond_dirt.png");
    }

    @Override
    public @Range(from = 1, to = Integer.MAX_VALUE) int getId() {
        return 1;
    }

    @Override
    public Component getName() {
        return Component.text("Diamond Dirt");
    }

    @Override
    public @Nullable List<Component> getLore() {
        return List.of(
                Component.text("A dirt made with... diamond?").style(Style.style(NamedTextColor.DARK_GRAY, TextDecoration.ITALIC.withState(false)))
        );
    }

    @Override
    public int getBlockId() {
        return 0;
    }
}
