package rip.diamond.moddedbukkit.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Instrument;

import java.util.List;
import java.util.Map;

@UtilityClass
public class NoteBlockSoundUtil {

    public static Map<Instrument, List<String>> BLOCK;

    static {
        //Credit: Instrument list from Oraxen
        //TODO: Note block place under note block, sound is different compare to vanilla
        BLOCK = Map.ofEntries(
                Map.entry(Instrument.BASS_DRUM, List.of("stone", "netherrack", "bedrock", "observer", "coral", "obsidian", "anchor", "quartz")),
                Map.entry(Instrument.SNARE_DRUM, List.of("sand", "gravel", "concrete_powder", "soul_soil")),
                Map.entry(Instrument.STICKS, List.of("glass", "sea_lantern", "beacon")),
                Map.entry(Instrument.BASS_GUITAR, List.of("wood")),
                Map.entry(Instrument.FLUTE, List.of("clay")),
                Map.entry(Instrument.BELL, List.of("gold_block")),
                Map.entry(Instrument.GUITAR, List.of("wool")),
                Map.entry(Instrument.CHIME, List.of("packed_ice")),
                Map.entry(Instrument.XYLOPHONE, List.of("bone_block")),
                Map.entry(Instrument.IRON_XYLOPHONE, List.of("iron_block")),
                Map.entry(Instrument.COW_BELL, List.of("soul_sand")),
                Map.entry(Instrument.DIDGERIDOO, List.of("pumpkin")),
                Map.entry(Instrument.BIT, List.of("emerald_block")),
                Map.entry(Instrument.BANJO, List.of("hay_bale")),
                Map.entry(Instrument.PLING, List.of("glowstone"))
        );
    }

}
