package rip.diamond.moddedbukkit.item;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import rip.diamond.moddedbukkit.ModdedBukkitPlugin;
import rip.diamond.moddedbukkit.block.ModdedBlock;
import rip.diamond.moddedbukkit.block.ModdedBlockModuleImpl;

@RequiredArgsConstructor
public class ModdedItemModuleListener implements Listener {

    private final ModdedBukkitPlugin plugin;
    private final ModdedItemModuleImpl module;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ItemStack itemStack = event.getItem();

        if (itemStack == null) {
            return;
        }

        String key = itemStack.getPersistentDataContainer().get(ModdedItem.KEY, PersistentDataType.STRING);

        //If key is null, then it means the ItemStack player interacts is not a custom item
        if (key == null) {
            return;
        }

        ModdedItem item = module.getItem(key);
        Action action = event.getAction();

        if (item instanceof ModdedPlaceableItem placeableItem && action == Action.RIGHT_CLICK_BLOCK) {
            int blockID = placeableItem.getBlockId();
            ModdedBlockModuleImpl blockModule = plugin.getModule(ModdedBlockModuleImpl.class);
            ModdedBlock moddedBlock = blockModule.getBlock(blockID);

            Preconditions.checkNotNull(moddedBlock);

            Player player = event.getPlayer();
            EquipmentSlot slot = event.getHand();
            Block block = event.getClickedBlock();
            BlockFace blockFace = event.getBlockFace();

            Preconditions.checkNotNull(block);

            blockModule.playerPlaceBlock(player, slot, itemStack, block, blockFace, moddedBlock);
        }
    }

}
