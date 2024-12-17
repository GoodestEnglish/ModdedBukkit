package rip.diamond.moddedbukkit.block;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import rip.diamond.moddedbukkit.ExamplePlugin;
import rip.diamond.moddedbukkit.item.DiamondDirtItem;

import java.io.InputStream;
import java.util.List;

public class DiamondDirtBlock implements ModdedBlock {

    public static int ID = 0;

    private final ModdedBlockHandler handler = new ModdedBlockHandler() {
        @Override
        public void onPlace(BlockPlaceEvent event) {
            event.getPlayer().sendMessage(Component.text("You placed " + getKey().toString()).color(NamedTextColor.GRAY));
        }

        @Override
        public void onBreak(BlockBreakEvent event) {
            event.getPlayer().sendMessage(Component.text("You broke " + getKey().toString()).color(NamedTextColor.GRAY));
        }
    };

    @Override
    public Key getKey() {
        return Key.key("example:diamond_dirt");
    }

    @Override
    public Key getTextureKey() {
        return Key.key("example:block/diamond_dirt");
    }

    @Override
    public InputStream getTextureResource() {
        return ExamplePlugin.INSTANCE.getResource("pack/textures/block/diamond_dirt.png");
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public ModdedBlockType getBlockType() {
        return ModdedBlockType.NOTE_BLOCK;
    }

    @Override
    public double getHardness() {
        return 10.0;
    }

    @Override
    public Sound getPlaceSound() {
        return Sound.sound(org.bukkit.Sound.BLOCK_GRASS_PLACE, Sound.Source.MASTER, 1f, 1f);
    }

    @Override
    public Sound getBreakSound() {
        return Sound.sound(org.bukkit.Sound.BLOCK_GRASS_BREAK, Sound.Source.MASTER, 1f, 1f);
    }

    @Override
    public List<ItemStack> getDrops(ItemStack tool) {
        return List.of(
                ExamplePlugin.INSTANCE.getItemModule().getItem(DiamondDirtItem.KEY.asString()).buildItemStack()
        );
    }

    @Override
    public ModdedBlockHandler getHandler() {
        return handler;
    }
}
