package rip.diamond.moddedbukkit.block;

import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.GameEvent;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import rip.diamond.moddedbukkit.ModdedBukkitPlugin;
import rip.diamond.moddedbukkit.util.ModdedLogger;

import java.util.HashMap;
import java.util.Map;

public class ModdedBlockModuleImpl implements ModdedBlockModule {

    private final Map<Integer, ModdedBlock> blocks = new HashMap<>();

    public ModdedBlockModuleImpl() {
        Bukkit.getPluginManager().registerEvents(new ModdedBlockModuleListener(this), ModdedBukkitPlugin.INSTANCE);
    }

    @Override
    public void registerBlock(ModdedBlock block) {
        int id = block.getId();

        if (blocks.containsKey(id)) {
            String key = block.getKey().asString();

            ModdedLogger.log("Cannot register block '" + key + "' because it is already registered");
            return;
        }

        blocks.put(id, block);
    }

    @Override
    public ModdedBlock getBlock(int id) {
        return blocks.get(id);
    }

    @Override
    public ModdedBlock getBlock(Block block) {
        BlockData blockData = block.getBlockData();
        int blockID = ModdedBlockData.fromBlockData(blockData);

        if (blockID < 0) {
            return null;
        }

        ModdedBlock moddedBlock = getBlock(blockID);

        if (moddedBlock == null) {
            throw new NullPointerException("Cannot find block with ID " + blockID);
        }

        return moddedBlock;
    }

    @Override
    public boolean isCustomBlock(Block block) {
        return getBlock(block) != null;
    }

    public void playerPlaceBlock(Player player, EquipmentSlot slot, ItemStack itemStack, Block clickedOn, BlockFace blockFace, ModdedBlock block) {
        BlockData blockData = block.getBukkitBlockData();
        Sound placeSound = block.getPlaceSound();
        World world = player.getWorld();
        Block toBeReplaced = clickedOn.isReplaceable() ? clickedOn : clickedOn.getRelative(blockFace);
        BlockData oldBlockData = toBeReplaced.getBlockData();

        toBeReplaced.setBlockData(blockData);

        //Trigger BlockPlaceEvent, to support other plugins so player won't be able to place blocks in disallowed area
        BlockPlaceEvent blockPlaceEvent = new BlockPlaceEvent(toBeReplaced, toBeReplaced.getState(), clickedOn, itemStack, player, true, slot);
        blockPlaceEvent.callEvent();
        if (blockPlaceEvent.isCancelled()) {
            //Set back the block data to the old state
            toBeReplaced.setBlockData(oldBlockData);
            return;
        }

        if (player.getGameMode() != GameMode.CREATIVE) {
            itemStack.subtract();
        }

        player.swingHand(slot);
        player.playSound(placeSound);
        world.sendGameEvent(player, GameEvent.BLOCK_PLACE, toBeReplaced.getLocation().toVector());
    }
}
