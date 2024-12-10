package rip.diamond.moddedbukkit.block;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class DiamondDirtBlock implements ModdedBlock {

    private final ModdedBlockHandler handler = new ModdedBlockHandler() {
        @Override
        public void onPlace(BlockPlaceEvent event) {
            event.getPlayer().sendMessage("You placed " + getKey().toString());
        }

        @Override
        public void onBreak(BlockBreakEvent event) {
            event.getPlayer().sendMessage("You broke " + getKey().toString());
        }
    };

    @Override
    public Key getKey() {
        return Key.key("example:diamond_dirt");
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public ModdedBlockType getBlockType() {
        return ModdedBlockType.NOTE_BLOCK;
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
    public ModdedBlockHandler getHandler() {
        return handler;
    }
}
