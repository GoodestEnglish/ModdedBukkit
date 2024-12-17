package rip.diamond.moddedbukkit.pack.modifier.implement;

import net.kyori.adventure.key.Key;
import rip.diamond.moddedbukkit.ModdedBukkitPlugin;
import rip.diamond.moddedbukkit.item.ModdedItem;
import rip.diamond.moddedbukkit.item.ModdedItemModuleImpl;
import rip.diamond.moddedbukkit.pack.modifier.ResourcePackModifier;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.model.*;

import java.util.Comparator;

public class ResourcePackPaperModelModifier extends ResourcePackModifier {
    public ResourcePackPaperModelModifier(ModdedBukkitPlugin plugin, ResourcePack pack) {
        super(plugin, pack);
    }

    @Override
    public void modify() {
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
            Key textureKey = item.getTextureKey();

            //If the namespace isn't minecraft, which means the item is displayed as a custom image, we have to create the image and import to the resource pack.
            if (!textureKey.namespace().equals("minecraft")) {
                insertTextureAndModel(pack, item);
            }

            paperModel.addOverride(ItemOverride.of(textureKey, ItemPredicate.customModelData(item.getId())));
        });

        Model model = paperModel.build();

        pack.model(model);
    }
}
