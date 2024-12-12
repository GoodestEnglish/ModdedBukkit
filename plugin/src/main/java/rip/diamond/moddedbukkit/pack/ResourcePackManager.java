package rip.diamond.moddedbukkit.pack;

import net.kyori.adventure.key.Key;
import rip.diamond.moddedbukkit.ModdedBukkitPlugin;
import rip.diamond.moddedbukkit.block.ModdedBlock;
import rip.diamond.moddedbukkit.block.ModdedBlockData;
import rip.diamond.moddedbukkit.block.ModdedBlockModuleImpl;
import rip.diamond.moddedbukkit.item.ModdedItem;
import rip.diamond.moddedbukkit.item.ModdedItemModuleImpl;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.blockstate.BlockState;
import team.unnamed.creative.blockstate.MultiVariant;
import team.unnamed.creative.blockstate.Variant;
import team.unnamed.creative.central.CreativeCentral;
import team.unnamed.creative.central.CreativeCentralProvider;
import team.unnamed.creative.central.event.pack.ResourcePackGenerateEvent;
import team.unnamed.creative.model.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ResourcePackManager {

    private final ModdedBukkitPlugin plugin;
    private final CreativeCentral central;

    public ResourcePackManager(ModdedBukkitPlugin plugin) {
        this.plugin = plugin;
        this.central = CreativeCentralProvider.get();

        listenToEvent();
    }

    private void listenToEvent() {
        central.eventBus().listen(plugin, ResourcePackGenerateEvent.class, event -> {
            ResourcePack pack = event.resourcePack();
            BlockState blockState = buildNoteBlockBlockState();
            Model paperModel = buildPaperModel();

            pack.packMeta(9, "ModdedBukkit Generated ResourcePack");
            pack.blockState(blockState);
            pack.model(paperModel);
        });
    }

    private BlockState buildNoteBlockBlockState() {
        Map<String, MultiVariant> variants = new HashMap<>();

        plugin.getModule(ModdedBlockModuleImpl.class).getBlocks().entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getKey)).forEachOrdered(entry -> {
            int id = entry.getKey();
            ModdedBlock block = entry.getValue();
            String details = ModdedBlockData.toBlockData(block.getBlockType(), id).getAsString().split("\\[")[1].split("]")[0];
            Key texture = block.getTexture();

            variants.put(details, MultiVariant.of(Variant.builder().model(texture).build()));
        });

        return BlockState.of(Key.key("minecraft:note_block"), variants);
    }

    private Model buildPaperModel() {
        Model.Builder paperModel = Model.model()
                .key(Key.key("minecraft:item/paper"))
                .parent(Key.key("minecraft:item/generated"))
                .textures(ModelTextures.builder()
                        .layers(
                                ModelTexture.ofKey(Key.key("minecraft:item/paper"))
                        )
                        .build()
                );

        plugin.getModule(ModdedItemModuleImpl.class).getItems().entrySet().stream().sorted(Comparator.comparingInt(entry -> entry.getValue().getId())).forEachOrdered(entry -> {
            ModdedItem item = entry.getValue();

            paperModel.addOverride(ItemOverride.of(item.getTexture(), ItemPredicate.customModelData(item.getId())));
        });

        return paperModel.build();
    }

}
