package rip.diamond.moddedbukkit.block;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.NoteBlock;

public interface ModdedBlockData {

    static BlockData toBlockData(ModdedBlockType type, int id) {
        switch (type) {
            case NOTE_BLOCK -> {return NoteBlockData.toBlockData(id);}
            case TRIPWIRE -> {return TripwireData.toBlockData(id);}
            default -> throw new IllegalStateException("Unexpected value: " + type.name());
        }
    }

    /**
     * All custom block ID should be positive, including 0.
     *
     * @param data The block data
     * @return The block ID
     */
    static int fromBlockData(BlockData data) {
        Material material = data.getMaterial();

        switch (material) {
            case NOTE_BLOCK -> {return NoteBlockData.fromBlockData(data);}
            case TRIPWIRE -> {return TripwireData.fromBlockData(data);}
            default -> {return Integer.MIN_VALUE;}
        }
    }

    interface NoteBlockData extends ModdedBlockData {

        static BlockData toBlockData(int id) {
            // Skip all BlockData with PIANO Instrument, 0 - 24 reserved for PIANO all notes but not powered
            id += 25;

            NoteBlock noteBlock = (NoteBlock) Bukkit.createBlockData(Material.NOTE_BLOCK);

            // Calculate instrument type and fetch the corresponding Instrument enum
            Instrument instrument = Instrument.getByType((byte) ((id % 400) / 25));
            Preconditions.checkNotNull(instrument);

            // Set instrument, note, and powered state
            noteBlock.setInstrument(instrument);
            noteBlock.setNote(new Note(id % 25));
            noteBlock.setPowered(id >= 400);

            return noteBlock;
        }

        static int fromBlockData(BlockData data) {
            NoteBlock noteBlock = (NoteBlock) data;

            // Start with the note ID
            int id = noteBlock.getNote().getId();

            // Add the instrument type multiplied by 25
            id += noteBlock.getInstrument().getType() * 25;

            // Add 400 if the block is powered
            if (noteBlock.isPowered()) {
                id += 400;
            }

            // Adjust the ID to exclude reserved IDs for PIANO Instrument
            return id - 25;
        }
    }


    interface TripwireData extends ModdedBlockData {
        static BlockData toBlockData(int id) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        static int fromBlockData(BlockData data) {
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }
}
