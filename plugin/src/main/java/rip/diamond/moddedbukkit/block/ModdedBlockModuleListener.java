package rip.diamond.moddedbukkit.block;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerInteractEvent;
import rip.diamond.moddedbukkit.util.NoteBlockSoundUtil;

import java.util.Map;

@RequiredArgsConstructor
public class ModdedBlockModuleListener implements Listener {

    private final ModdedBlockModuleImpl module;

    @EventHandler
    public void onPhysics(BlockPhysicsEvent event) {
        //Prevent note block BlockData change because of the block under/top change the instrument type.
        //For example, placing a packed ice under note block will change the instrument. But the
        // instrument the system tries to change is reserved for custom block. So we have to cancel the changes.
        Block block = event.getBlock();
        Block aboveBlock = block.getRelative(BlockFace.UP);
        Block belowBlock = block.getRelative(BlockFace.DOWN);

        if (block.getType() == Material.NOTE_BLOCK || aboveBlock.getType() == Material.NOTE_BLOCK || belowBlock.getType() == Material.NOTE_BLOCK) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        ModdedBlock moddedBlock = module.getBlock(block);

        //When placing a note block, the block data (specificity, instrument type) will change according to the block underneath.
        //By set a new note block BlockData when placing note block can force the note block instrument type to be PIANO only.
        if (block.getType() == Material.NOTE_BLOCK && moddedBlock == null) {
            block.setBlockData(Bukkit.createBlockData(Material.NOTE_BLOCK));
        }

        //If the block player is placing is a custom block, run custom block handler
        if (moddedBlock != null) {
            moddedBlock.getHandler().onPlace(event);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        ModdedBlock moddedBlock = module.getBlock(block);

        //If the block player is breaking is a custom block, we do the following:
        // - Disable default item drops, prevent player gain default note block and tripwire
        // - Run custom block handler
        if (moddedBlock != null) {
            event.setDropItems(false);
            moddedBlock.getHandler().onBreak(event);
        }
    }

    //Disable custom block action
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        Material material = block == null ? null : block.getType();
        Action action = event.getAction();
        BlockData blockData = block == null ? null : block.getBlockData();

        //Custom Note Block
        if (material == Material.NOTE_BLOCK && action == Action.RIGHT_CLICK_BLOCK && ((NoteBlock) blockData).getInstrument() != Instrument.PIANO) {
            event.setCancelled(true);
        }
        //Custom Tripwire Block
        else if (material == Material.TRIPWIRE && action == Action.PHYSICAL) {
            event.setCancelled(true);
        }
    }

    //Play default note block sound according to the block under
    @EventHandler
    public void onPlay(final NotePlayEvent event) {
        Instrument instrument = event.getInstrument();

        if (instrument != Instrument.PIANO) {
            event.setCancelled(true);
        } else {
            String blockType = event.getBlock().getRelative(BlockFace.DOWN).getType().name().toLowerCase();
            Instrument fakeInstrument = NoteBlockSoundUtil.BLOCK.entrySet().stream().filter(entry -> entry.getValue().contains(blockType)).map(Map.Entry::getKey).findFirst().orElse(Instrument.PIANO);

            event.setInstrument(fakeInstrument);
        }
    }
}
