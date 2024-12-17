package rip.diamond.moddedbukkit.pack;

import rip.diamond.moddedbukkit.ModdedBukkitPlugin;
import rip.diamond.moddedbukkit.pack.modifier.implement.ResourcePackNoteBlockBlockStateModifier;
import rip.diamond.moddedbukkit.pack.modifier.implement.ResourcePackPaperModelModifier;
import rip.diamond.moddedbukkit.pack.modifier.implement.ResourcePackSoundRegistryModifier;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.central.CreativeCentral;
import team.unnamed.creative.central.CreativeCentralProvider;
import team.unnamed.creative.central.event.pack.ResourcePackGenerateEvent;

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

            new ResourcePackPaperModelModifier(plugin, pack).modify();
            new ResourcePackNoteBlockBlockStateModifier(plugin, pack).modify();
            new ResourcePackSoundRegistryModifier(plugin, pack).modify();

            pack.packMeta(9, "ModdedBukkit Generated ResourcePack");
        });
    }

}
