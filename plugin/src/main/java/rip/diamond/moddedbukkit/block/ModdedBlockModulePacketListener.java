package rip.diamond.moddedbukkit.block;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.attribute.Attributes;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.DiggingAction;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerDigging;
import com.github.retrooper.packetevents.wrapper.play.server.*;
import lombok.RequiredArgsConstructor;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import rip.diamond.moddedbukkit.ModdedBukkitPlugin;

import java.util.List;

@RequiredArgsConstructor
public class ModdedBlockModulePacketListener implements PacketListener {

    private final ModdedBukkitPlugin plugin;
    private final ModdedBlockModuleImpl module;

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

            switch (action) {
                case START_DIGGING -> {
                    double speed = 1 / moddedBlock.getHardness();

                    WrapperPlayServerUpdateAttributes updateAttributesPacket = new WrapperPlayServerUpdateAttributes(player.getEntityId(), List.of(new WrapperPlayServerUpdateAttributes.Property(Attributes.BLOCK_BREAK_SPEED, speed, List.of())));
                    PacketEvents.getAPI().getPlayerManager().sendPacket(player, updateAttributesPacket);
                }
                case CANCELLED_DIGGING, FINISHED_DIGGING -> {
                    WrapperPlayServerUpdateAttributes updateAttributesPacket = new WrapperPlayServerUpdateAttributes(player.getEntityId(), List.of(new WrapperPlayServerUpdateAttributes.Property(Attributes.BLOCK_BREAK_SPEED, player.getAttribute(Attribute.PLAYER_BLOCK_BREAK_SPEED).getValue(), List.of())));
                    PacketEvents.getAPI().getPlayerManager().sendPacket(player, updateAttributesPacket);
                }
            }
        }
    }
}
