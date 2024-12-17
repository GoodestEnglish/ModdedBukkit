package rip.diamond.moddedbukkit.block;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import net.kyori.adventure.sound.Sound;
import org.apache.commons.lang3.Range;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import rip.diamond.moddedbukkit.ModdedBukkitPlugin;
import rip.diamond.moddedbukkit.util.BlockUtil;
import rip.diamond.moddedbukkit.util.ModdedLogger;
import rip.diamond.moddedbukkit.util.SoundUtil;

import java.util.HashMap;
import java.util.Map;

public class ModdedBlockModuleImpl implements ModdedBlockModule {

    private final ModdedBukkitPlugin plugin;
    private final Map<Integer, ModdedBlock> blocks = new HashMap<>();

    public ModdedBlockModuleImpl(ModdedBukkitPlugin plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(new ModdedBlockModuleListener(this), plugin);
        PacketEvents.getAPI().getEventManager().registerListener(new ModdedBlockModulePacketListener(plugin, this), PacketListenerPriority.NORMAL);
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
    public Map<Integer, ModdedBlock> getBlocks() {
        return blocks;
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

    public void playerPlaceBlock(Player player, EquipmentSlot slot, ItemStack itemStack, Block clickedOn, BlockFace blockFace, ModdedBlock moddedBlock) {
        BlockData blockData = moddedBlock.getBukkitBlockData();
        Sound placeSound = moddedBlock.getPlaceSound();
        Block toBeReplaced = clickedOn.isReplaceable() ? clickedOn : clickedOn.getRelative(blockFace);
        Location toBeReplacedCenterLocation = toBeReplaced.getLocation().toCenterLocation();
        BlockData oldBlockData = toBeReplaced.getBlockData();

        //Do not allow placing if the moddedBlock player clicked on is interactable and is not sneaking
        if (BlockUtil.isInteractable(clickedOn) && !player.isSneaking()) {
            return;
        }
        //Do not allow placing if y level is not within the world
        if (!Range.between(toBeReplaced.getWorld().getMinHeight(), toBeReplaced.getWorld().getMaxHeight() - 1).contains(toBeReplaced.getY())) {
            return;
        }
        //Do not allow placing if there's any entities standing near the moddedBlock
        if (toBeReplacedCenterLocation.getNearbyLivingEntities(0.5, 0.5, 0.5).stream().anyMatch(entity -> !(entity instanceof Player p && p.getGameMode() == GameMode.SPECTATOR))) {
            return;
        }
        //Do not allow placing if there's a moddedBlock in that location, but isn't replaceable (For example: lever, skull)
        if (toBeReplaced.getType() != Material.AIR && !toBeReplaced.isReplaceable()) {
            return;
        }

        toBeReplaced.setBlockData(blockData);

        //Trigger BlockPlaceEvent, to support other plugins so player won't be able to place blocks in disallowed area
        BlockPlaceEvent event = new BlockPlaceEvent(toBeReplaced, toBeReplaced.getState(), clickedOn, itemStack, player, true, slot);
        event.callEvent();

        //If event is cancelled, we set back the moddedBlock data to the old state, to prevent moddedBlock changes
        if (event.isCancelled()) {
            //Set back the moddedBlock data to the old state
            toBeReplaced.setBlockData(oldBlockData);
            return;
        }

        //Start making player place the moddedBlock
        if (player.getGameMode() != GameMode.CREATIVE) {
            itemStack.subtract();
        }
        player.swingHand(slot);
        SoundUtil.playSound(toBeReplacedCenterLocation, placeSound);
    }
}
