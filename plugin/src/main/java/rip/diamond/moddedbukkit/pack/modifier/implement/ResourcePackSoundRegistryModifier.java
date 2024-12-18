package rip.diamond.moddedbukkit.pack.modifier.implement;

import net.kyori.adventure.key.Key;
import rip.diamond.moddedbukkit.ModdedBukkitPlugin;
import rip.diamond.moddedbukkit.pack.modifier.ResourcePackModifier;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.sound.Sound;
import team.unnamed.creative.sound.SoundEntry;
import team.unnamed.creative.sound.SoundEvent;
import team.unnamed.creative.sound.SoundRegistry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class ResourcePackSoundRegistryModifier extends ResourcePackModifier {

    public static Key VANILLA_WOOD_PLACE = Key.key("minecraft:block.wood.place");
    public static Key VANILLA_WOOD_BREAK = Key.key("minecraft:block.wood.break");
    public static Key VANILLA_WOOD_HIT = Key.key("minecraft:block.wood.hit");
    public static Key VANILLA_WOOD_STEP = Key.key("minecraft:block.wood.step");
    public static Key VANILLA_WOOD_FALL = Key.key("minecraft:block.wood.fall");
    public static Key VANILLA_STONE_PLACE = Key.key("minecraft:block.stone.place");
    public static Key VANILLA_STONE_BREAK = Key.key("minecraft:block.stone.break");
    public static Key VANILLA_STONE_HIT = Key.key("minecraft:block.stone.hit");
    public static Key VANILLA_STONE_STEP = Key.key("minecraft:block.stone.step");
    public static Key VANILLA_STONE_FALL = Key.key("minecraft:block.stone.fall");

    public static Key REPLACED_WOOD_PLACE = Key.key("minecraft:moddedbukkit.wood.place");
    public static Key REPLACED_WOOD_BREAK = Key.key("minecraft:moddedbukkit.wood.break");
    public static Key REPLACED_WOOD_HIT = Key.key("minecraft:moddedbukkit.wood.hit");
    public static Key REPLACED_WOOD_STEP = Key.key("minecraft:moddedbukkit.wood.step");
    public static Key REPLACED_WOOD_FALL = Key.key("minecraft:moddedbukkit.wood.fall");
    public static Key REPLACED_STONE_PLACE = Key.key("minecraft:moddedbukkit.stone.place");
    public static Key REPLACED_STONE_BREAK = Key.key("minecraft:moddedbukkit.stone.break");
    public static Key REPLACED_STONE_HIT = Key.key("minecraft:moddedbukkit.stone.hit");
    public static Key REPLACED_STONE_STEP = Key.key("minecraft:moddedbukkit.stone.step");
    public static Key REPLACED_STONE_FALL = Key.key("minecraft:moddedbukkit.stone.fall");

    public ResourcePackSoundRegistryModifier(ModdedBukkitPlugin plugin, ResourcePack pack) {
        super(plugin, pack);
    }

    @Override
    public void modify() {
        Set<SoundEvent> soundEvents = new HashSet<>();

        //Remove note block default sounds
        List.of(VANILLA_WOOD_PLACE, VANILLA_WOOD_BREAK, VANILLA_WOOD_HIT, VANILLA_WOOD_STEP, VANILLA_WOOD_FALL,
                VANILLA_STONE_PLACE, VANILLA_STONE_BREAK, VANILLA_STONE_HIT, VANILLA_STONE_STEP, VANILLA_STONE_FALL).forEach(soundKey -> {
            soundEvents.add(SoundEvent.soundEvent()
                    .key(soundKey)
                    .sounds(List.of())
                    .replace(true)
                    .build());
        });
        //Add sounds for all wood blocks
        soundEvents.add(
                SoundEvent.soundEvent()
                        .key(REPLACED_WOOD_PLACE)
                        .sounds(Stream.of("dig/wood1", "dig/wood2", "dig/wood3", "dig/wood4")
                                .map(s -> SoundEntry.soundEntry(Sound.sound(Key.key("minecraft", s), Writable.EMPTY)))
                                .toList())
                        .build()
        );
        soundEvents.add(
                SoundEvent.soundEvent()
                        .key(REPLACED_WOOD_BREAK)
                        .sounds(Stream.of("dig/wood1", "dig/wood2", "dig/wood3", "dig/wood4")
                                .map(s -> SoundEntry.soundEntry(Sound.sound(Key.key("minecraft", s), Writable.EMPTY)))
                                .toList())
                        .build()
        );
        soundEvents.add(
                SoundEvent.soundEvent()
                        .key(REPLACED_WOOD_HIT)
                        .sounds(Stream.of("step/wood1", "step/wood2", "step/wood3", "step/wood4", "step/wood5", "step/wood6")
                                .map(s -> SoundEntry.soundEntry(Sound.sound(Key.key("minecraft", s), Writable.EMPTY)))
                                .toList())
                        .build()
        );
        soundEvents.add(
                SoundEvent.soundEvent()
                        .key(REPLACED_WOOD_STEP)
                        .sounds(Stream.of("step/wood1", "step/wood2", "step/wood3", "step/wood4", "step/wood5", "step/wood6")
                                .map(s -> SoundEntry.soundEntry(Sound.sound(Key.key("minecraft", s), Writable.EMPTY)))
                                .toList())
                        .build()
        );
        soundEvents.add(
                SoundEvent.soundEvent()
                        .key(REPLACED_WOOD_FALL)
                        .sounds(Stream.of("step/wood1", "step/wood2", "step/wood3", "step/wood4", "step/wood5", "step/wood6")
                                .map(s -> SoundEntry.soundEntry(Sound.sound(Key.key("minecraft", s), Writable.EMPTY)))
                                .toList())
                        .build()
        );
        soundEvents.add(
                SoundEvent.soundEvent()
                        .key(REPLACED_STONE_PLACE)
                        .sounds(Stream.of("dig/stone1", "dig/stone2", "dig/stone3", "dig/stone4")
                                .map(s -> SoundEntry.soundEntry(Sound.sound(Key.key("minecraft", s), Writable.EMPTY)))
                                .toList())
                        .build()
        );
        soundEvents.add(
                SoundEvent.soundEvent()
                        .key(REPLACED_STONE_BREAK)
                        .sounds(Stream.of("dig/stone1", "dig/stone2", "dig/stone3", "dig/stone4")
                                .map(s -> SoundEntry.soundEntry(Sound.sound(Key.key("minecraft", s), Writable.EMPTY)))
                                .toList())
                        .build()
        );
        soundEvents.add(
                SoundEvent.soundEvent()
                        .key(REPLACED_STONE_HIT)
                        .sounds(Stream.of("step/stone1", "step/stone2", "step/stone3", "step/stone4", "step/stone5", "step/stone6")
                                .map(s -> SoundEntry.soundEntry(Sound.sound(Key.key("minecraft", s), Writable.EMPTY)))
                                .toList())
                        .build()
        );
        soundEvents.add(
                SoundEvent.soundEvent()
                        .key(REPLACED_STONE_STEP)
                        .sounds(Stream.of("step/stone1", "step/stone2", "step/stone3", "step/stone4", "step/stone5", "step/stone6")
                                .map(s -> SoundEntry.soundEntry(Sound.sound(Key.key("minecraft", s), Writable.EMPTY)))
                                .toList())
                        .build()
        );
        soundEvents.add(
                SoundEvent.soundEvent()
                        .key(REPLACED_STONE_FALL)
                        .sounds(Stream.of("step/stone1", "step/stone2", "step/stone3", "step/stone4", "step/stone5", "step/stone6")
                                .map(s -> SoundEntry.soundEntry(Sound.sound(Key.key("minecraft", s), Writable.EMPTY)))
                                .toList())
                        .build()
        );


        SoundRegistry soundRegistry = SoundRegistry.soundRegistry("minecraft", soundEvents);
        pack.soundRegistry(soundRegistry);
    }
}
