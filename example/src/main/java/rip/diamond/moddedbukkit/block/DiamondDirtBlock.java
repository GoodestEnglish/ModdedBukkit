package rip.diamond.moddedbukkit.block;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
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
        return Sound.sound(org.bukkit.Sound.BLOCK_ROOTED_DIRT_PLACE, Sound.Source.MASTER, 1.0f, 0.8f);
    }

    @Override
    public Sound getBreakSound() {
        return Sound.sound(org.bukkit.Sound.BLOCK_ROOTED_DIRT_BREAK, Sound.Source.MASTER, 1.0f, 0.8f);
    }

    @Override
    public Sound getHitSound() {
        return Sound.sound(org.bukkit.Sound.BLOCK_ROOTED_DIRT_HIT, Sound.Source.MASTER, 0.25f, 0.5f);
    }

    @Override
    public Sound getStepSound() {
        return Sound.sound(org.bukkit.Sound.BLOCK_ROOTED_DIRT_STEP, Sound.Source.MASTER, 0.15f, 1.0f);
    }

    @Override
    public Sound getFallSound() {
        return Sound.sound(org.bukkit.Sound.BLOCK_ROOTED_DIRT_FALL, Sound.Source.MASTER, 0.5f, 0.75f);
    }

    @Override
    public List<ItemStack> getDrops(@Nullable ItemStack tool) {
        return List.of(
                ExamplePlugin.INSTANCE.getItemModule().getItem(DiamondDirtItem.KEY.asString()).buildItemStack()
        );
    }

    @Override
    public ModdedBlockHandler getHandler() {
        return handler;
    }
}
