package rip.diamond.moddedbukkit.pack.modifier.implement;

import net.kyori.adventure.key.Key;
import rip.diamond.moddedbukkit.ModdedBukkitPlugin;
import rip.diamond.moddedbukkit.block.ModdedBlock;
import rip.diamond.moddedbukkit.block.ModdedBlockData;
import rip.diamond.moddedbukkit.block.ModdedBlockModuleImpl;
import rip.diamond.moddedbukkit.block.ModdedBlockType;
import rip.diamond.moddedbukkit.pack.modifier.ResourcePackModifier;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.blockstate.BlockState;
import team.unnamed.creative.blockstate.MultiVariant;
import team.unnamed.creative.blockstate.Variant;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ResourcePackNoteBlockBlockStateModifier extends ResourcePackModifier {
    public ResourcePackNoteBlockBlockStateModifier(ModdedBukkitPlugin plugin, ResourcePack pack) {
        super(plugin, pack);
    }

    @Override
    public void modify() {
        Map<String, MultiVariant> variants = new HashMap<>();

        plugin.getModule(ModdedBlockModuleImpl.class).getBlocks().entrySet().stream().filter(entry -> entry.getValue().getBlockType() == ModdedBlockType.NOTE_BLOCK).sorted(Comparator.comparingInt(Map.Entry::getKey)).forEachOrdered(entry -> {
            int id = entry.getKey();
            ModdedBlock block = entry.getValue();
            String details = ModdedBlockData.toBlockData(block.getBlockType(), id).getAsString().split("\\[")[1].split("]")[0];
            Key textureKey = block.getTextureKey();

            //If the namespace isn't minecraft, which means the item is displayed as a custom image, we have to create the image and import to the resource pack.
            if (!textureKey.namespace().equals("minecraft")) {
                insertTextureAndModel(pack, block);
            }

            variants.put(details, MultiVariant.of(Variant.builder().model(textureKey).build()));
        });

        BlockState blockState = BlockState.of(Key.key("minecraft:note_block"), variants);

        pack.blockState(blockState);
    }
}
