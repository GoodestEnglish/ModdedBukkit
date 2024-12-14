package rip.diamond.moddedbukkit.block;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.DiggingAction;
import com.github.retrooper.packetevents.protocol.player.InteractionHand;
import com.github.retrooper.packetevents.protocol.potion.PotionTypes;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientAnimation;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerDigging;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerBlockBreakAnimation;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityEffect;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerRemoveEntityEffect;
import lombok.RequiredArgsConstructor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import rip.diamond.moddedbukkit.ModdedBukkitPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class ModdedBlockModulePacketListener implements PacketListener {

    private final ModdedBukkitPlugin plugin;
    private final ModdedBlockModuleImpl module;

    public static String BLOCK_METADATA = "block-status";

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        Player player = event.getPlayer();

        if (event.getPacketType() == PacketType.Play.Client.PLAYER_DIGGING) {
            //Ignore player in creative mode because player in creative mode can break block instantly
            if (player.getGameMode() == GameMode.CREATIVE) {
                return;
            }

            WrapperPlayClientPlayerDigging packet = new WrapperPlayClientPlayerDigging(event);
            DiggingAction action = packet.getAction();
            Vector3i vector3i = packet.getBlockPosition();
            Block block = player.getWorld().getBlockAt(vector3i.x, vector3i.y, vector3i.z);
            ModdedBlock moddedBlock = module.getBlock(block);

            //If moddedBlock is null, it means the block isn't a custom block
            if (moddedBlock == null) {
                return;
            }

            //TODO: Possible to change the hardness of the note block to unbreakable? So that I don't have to use this hacky entity effect packet to simulate block is unbreakable
            switch (action) {
                case START_DIGGING -> {
                    //Setup block metadata
                    if (block.getMetadata(BLOCK_METADATA).isEmpty()) {
                        block.setMetadata(BLOCK_METADATA, new FixedMetadataValue(plugin, new HashMap<UUID, BreakStatus>()));
                    }

                    Map<UUID, BreakStatus> map = getBlockBreakStatus(block);
                    map.put(player.getUniqueId(), new BreakStatus());

                    WrapperPlayServerEntityEffect entityEffectPacket = new WrapperPlayServerEntityEffect(player.getEntityId(), PotionTypes.MINING_FATIGUE, Integer.MAX_VALUE, Integer.MAX_VALUE, (byte) 0);
                    PacketEvents.getAPI().getPlayerManager().sendPacket(player, entityEffectPacket);
                }
                case CANCELLED_DIGGING, FINISHED_DIGGING -> {
                    Map<UUID, BreakStatus> map = getBlockBreakStatus(block);
                    map.remove(player.getUniqueId());

                    WrapperPlayServerRemoveEntityEffect removeEntityEffectPacket = new WrapperPlayServerRemoveEntityEffect(player.getEntityId(), PotionTypes.MINING_FATIGUE);
                    PacketEvents.getAPI().getPlayerManager().sendPacket(player, removeEntityEffectPacket);
                }
            }
        } else if (event.getPacketType() == PacketType.Play.Client.ANIMATION) {
            WrapperPlayClientAnimation packet = new WrapperPlayClientAnimation(event);
            InteractionHand hand = packet.getHand();

            if (hand != InteractionHand.MAIN_HAND) {
                return;
            }

            Block block = player.getTargetBlockExact(5, FluidCollisionMode.NEVER);

            //If block is null, it means player isn't trying to break anything
            if (block == null) {
                return;
            }

            ModdedBlock moddedBlock = module.getBlock(block);

            //If moddedBlock is null, it means the block isn't a custom block
            if (moddedBlock == null) {
                return;
            }

            BreakStatus breakStatus = getBlockBreakStatus(block).get(player.getUniqueId());
            int stage;

            if (breakStatus == null) {
                stage = -1;
            } else {
                breakStatus.tryToTick(moddedBlock.getHardness());
                stage = breakStatus.stage;
            }

            if (stage > 9) {
                Bukkit.getServer().getScheduler().runTask(plugin, () -> {
                    module.playerBreakBlock(player, block);
                });
            } else {
                //Simulate the block break status by sending a break animation packet to the player
                WrapperPlayServerBlockBreakAnimation blockBreakAnimationPacket = new WrapperPlayServerBlockBreakAnimation(player.getEntityId(), new Vector3i(block.getX(), block.getY(), block.getZ()), (byte) stage);
                PacketEvents.getAPI().getPlayerManager().sendPacket(player, blockBreakAnimationPacket);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Map<UUID, BreakStatus> getBlockBreakStatus(Block block) {
        return (Map<UUID, BreakStatus>) block.getMetadata(BLOCK_METADATA).getFirst().value();
    }

    private static class BreakStatus {
        public int stage = 0;
        public long lastTick = System.currentTimeMillis();

        public void tryToTick(double hardness) {
            if (System.currentTimeMillis() - lastTick > hardness * 100) {
                lastTick = System.currentTimeMillis();
                stage++;
            }
        }
    }
}
