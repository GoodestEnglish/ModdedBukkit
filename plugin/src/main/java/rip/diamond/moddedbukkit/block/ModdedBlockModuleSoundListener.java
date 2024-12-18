package rip.diamond.moddedbukkit.block;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.attribute.Attributes;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerUpdateAttributes;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageAbortEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.GenericGameEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import rip.diamond.moddedbukkit.ModdedBukkitPlugin;
import rip.diamond.moddedbukkit.util.BlockUtil;
import rip.diamond.moddedbukkit.util.SoundUtil;

import javax.swing.plaf.SplitPaneUI;
import java.util.Iterator;
import java.util.List;

/**
 * This class handles all block sounds.
 * <p>
 * Because some of the sounds are removed because of custom block support, we have to handle sounds with a listener.
 */
@RequiredArgsConstructor
public class ModdedBlockModuleSoundListener implements Listener {

    private static final String BLOCK_BREAK_SOUND_TASK = "block-break-sound-task";

    private final ModdedBukkitPlugin plugin;
    private final ModdedBlockModuleImpl module;

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        ModdedBlock moddedBlock = module.getBlock(block);
        Location centerLocation = block.getLocation().toCenterLocation();

        if (moddedBlock != null) {
            SoundUtil.playSound(centerLocation, moddedBlock.getBreakSound());
            return;
        }

        Key soundkey = block.getBlockSoundGroup().getBreakSound().getKey();
        Key replacedSoundKey = SoundUtil.REPLACE_TO.get(soundkey);

        if (replacedSoundKey != null) {
            SoundUtil.playSound(centerLocation, replacedSoundKey, 1.0f, 0.8f);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        ModdedBlock moddedBlock = module.getBlock(block);
        Location centerLocation = block.getLocation().toCenterLocation();

        if (moddedBlock != null) {
            SoundUtil.playSound(centerLocation, moddedBlock.getPlaceSound());
            return;
        }

        Key soundkey = block.getBlockSoundGroup().getPlaceSound().getKey();
        Key replacedSoundKey = SoundUtil.REPLACE_TO.get(soundkey);

        if (replacedSoundKey != null) {
            SoundUtil.playSound(centerLocation, replacedSoundKey, 1.0f, 0.8f);
        }
    }

    @EventHandler
    public void onGame(final GenericGameEvent event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof LivingEntity)) {
            return;
        }

        Block block = BlockUtil.getBlockStandingOn(entity);

        if (block == null) {
            return;
        }

        Location location = entity.getLocation();
        GameEvent gameEvent = event.getEvent();
        ModdedBlock moddedBlock = module.getBlock(block);

        if (gameEvent == GameEvent.STEP) {
            if (moddedBlock != null) {
                SoundUtil.playSound(location, moddedBlock.getStepSound());
                return;
            }

            Key soundkey = block.getBlockSoundGroup().getStepSound().getKey();
            Key replacedSoundKey = SoundUtil.REPLACE_TO.get(soundkey);

            if (replacedSoundKey != null) {
                SoundUtil.playSound(location, replacedSoundKey, 0.15f, 1.0f);
            }
        } else if (gameEvent == GameEvent.HIT_GROUND) {
            if (moddedBlock != null) {
                SoundUtil.playSound(location, moddedBlock.getFallSound());
                return;
            }

            Key soundkey = block.getBlockSoundGroup().getFallSound().getKey();
            Key replacedSoundKey = SoundUtil.REPLACE_TO.get(soundkey);

            if (replacedSoundKey != null) {
                SoundUtil.playSound(location, replacedSoundKey, 0.5f, 0.75f);
            }
        }
    }

    @EventHandler
    public void onDamage(BlockDamageEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location centerLocation = block.getLocation().toCenterLocation();
        ModdedBlock moddedBlock = module.getBlock(block);

        Sound hitSound;

        if (moddedBlock != null) {
            hitSound = moddedBlock.getHitSound();
        } else {
            Key soundkey = block.getBlockSoundGroup().getHitSound().getKey();
            Key replacedSoundKey = SoundUtil.REPLACE_TO.get(soundkey);

            if (replacedSoundKey == null) {
                return;
            }

            hitSound = Sound.sound(replacedSoundKey, Sound.Source.BLOCK, 0.25f, 0.5f);
        }

        ModdedBlockModuleBlockBreakingSoundTask task = new ModdedBlockModuleBlockBreakingSoundTask(centerLocation, hitSound);

        task.runTaskTimer(plugin, 2L, 4L);
        player.setMetadata(BLOCK_BREAK_SOUND_TASK, new FixedMetadataValue(plugin, task));
    }

    @EventHandler
    public void onDamageAbort(BlockDamageAbortEvent event) {
        Player player = event.getPlayer();

        for (MetadataValue value : player.getMetadata(BLOCK_BREAK_SOUND_TASK)) {
            if (value.value() instanceof ModdedBlockModuleBlockBreakingSoundTask task) {
                task.cancel();
            }
        }

        player.removeMetadata(BLOCK_BREAK_SOUND_TASK, plugin);
    }
}
