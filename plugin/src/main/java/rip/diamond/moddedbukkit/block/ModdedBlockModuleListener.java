package rip.diamond.moddedbukkit.block;

import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import rip.diamond.moddedbukkit.util.NoteBlockSoundUtil;

import java.util.List;
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
        ItemStack item = event.getItemInHand();

        //If the item in hand is a note block, then place it without applying the physics
        //This is to prevent block physics update (specificity, block data like instrument type) when note block is placed, causing note block instrument change.
        if (item.getType() == Material.NOTE_BLOCK) {
            block.setType(Material.NOTE_BLOCK, false);
            return;
        }

        ModdedBlock moddedBlock = module.getBlock(block);

        //If the block player is placing is a custom block, run custom block handler
        if (moddedBlock != null) {
            moddedBlock.getHandler().onPlace(event);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        ModdedBlock moddedBlock = module.getBlock(block);
        ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();

        //If the block player is breaking is a custom block, we do the following:
        // - Disable default item drops, prevent player gain default note block and tripwire
        // - Run custom block handler
        if (moddedBlock != null) {
            event.setDropItems(false);
            moddedBlock.getHandler().onBreak(event);

            if (!event.isCancelled() && player.getGameMode() != GameMode.CREATIVE) {
                moddedBlock.getDrops(tool).forEach(itemStack -> block.getWorld().dropItemNaturally(block.getLocation().toCenterLocation(), itemStack));
            }
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
            //TODO: cancel the event makes player cannot place block
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

    @EventHandler
    public void onExplode(BlockExplodeEvent event) {
        List<Block> blocks = event.blockList();

        module.simulateExplodeBlockDrop(blocks);
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        List<Block> blocks = event.blockList();

        module.simulateExplodeBlockDrop(blocks);
    }
}
