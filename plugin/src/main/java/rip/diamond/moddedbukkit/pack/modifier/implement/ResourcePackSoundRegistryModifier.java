package rip.diamond.moddedbukkit.pack.modifier.implement;

import net.kyori.adventure.key.Key;
import rip.diamond.moddedbukkit.ModdedBukkitPlugin;
import rip.diamond.moddedbukkit.pack.modifier.ResourcePackModifier;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.sound.SoundEvent;
import team.unnamed.creative.sound.SoundRegistry;

import java.util.List;
import java.util.stream.Collectors;

public class ResourcePackSoundRegistryModifier extends ResourcePackModifier {

    public ResourcePackSoundRegistryModifier(ModdedBukkitPlugin plugin, ResourcePack pack) {
        super(plugin, pack);
    }

    @Override
    public void modify() {
        List<String> toBeRemovedSound = List.of("block.wood.break", "block.wood.fall", "block.wood.hit", "block.wood.place", "block.wood.step");
        SoundRegistry soundRegistry = SoundRegistry.soundRegistry("minecraft", toBeRemovedSound.stream()
                .map(soundName -> SoundEvent.soundEvent()
                        .key(Key.key("minecraft:" + soundName))
                        .sounds(List.of())
                        .replace(true)
                        .build()
                )
                .collect(Collectors.toSet()));

        pack.soundRegistry(soundRegistry);
    }
}
