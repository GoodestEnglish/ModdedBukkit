package rip.diamond.moddedbukkit.block;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.attribute.Attributes;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerUpdateAttributes;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
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

    //Disable custom block action, and allow block placing near custom note block
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        Material blockMaterial = block == null ? null : block.getType();
        Action action = event.getAction();
        BlockData blockData = block == null ? null : block.getBlockData();
        BlockFace blockFace = event.getBlockFace();
        ItemStack item = event.getItem();

        //Custom Note Block
        if (blockMaterial == Material.NOTE_BLOCK && action == Action.RIGHT_CLICK_BLOCK && ((NoteBlock) blockData).getInstrument() != Instrument.PIANO) {
            event.setCancelled(true);

            if (item != null && item.getType().isBlock()) {
                module.simulatePlayerPlaceBlock(player, event.getHand(), item, block, blockFace);
            }
        }
        //Custom Tripwire Block
        else if (blockMaterial == Material.TRIPWIRE && action == Action.PHYSICAL) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(BlockDamageEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        ModdedBlock moddedBlock = module.getBlock(block);

        if (moddedBlock == null) {
            return;
        }

        double speed = 1 / moddedBlock.getHardness();

        WrapperPlayServerUpdateAttributes updateAttributesPacket = new WrapperPlayServerUpdateAttributes(player.getEntityId(), List.of(new WrapperPlayServerUpdateAttributes.Property(Attributes.BLOCK_BREAK_SPEED, speed, List.of())));
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, updateAttributesPacket);
    }

    @EventHandler
    public void onDamageAbort(BlockDamageAbortEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (module.isCustomBlock(block)) {
            return;
        }

        WrapperPlayServerUpdateAttributes updateAttributesPacket = new WrapperPlayServerUpdateAttributes(player.getEntityId(), List.of(new WrapperPlayServerUpdateAttributes.Property(Attributes.BLOCK_BREAK_SPEED, player.getAttribute(Attribute.PLAYER_BLOCK_BREAK_SPEED).getValue(), List.of())));
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, updateAttributesPacket);
    }

    //Play default note block sound according to the block under
    @EventHandler
    public void onPlay(final NotePlayEvent event) {
        Instrument instrument = event.getInstrument();

        if (instrument != Instrument.PIANO) {
            event.setCancelled(true);
        } else {
            Block underBlock = event.getBlock().getRelative(BlockFace.DOWN);

            //If the block is a custom block, we don't change the instrument
            if (module.isCustomBlock(underBlock)) {
                return;
            }

            String blockType = underBlock.getType().name().toLowerCase();
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
