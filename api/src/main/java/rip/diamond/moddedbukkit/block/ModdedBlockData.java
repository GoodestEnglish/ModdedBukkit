package rip.diamond.moddedbukkit.block;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.NoteBlock;

/**
 * Interface for creating block data and retrieve block data.
 */
public interface ModdedBlockData {

    /**
     * Converts a custom block type and ID to Bukkit BlockData.
     *
     * @param type The modded block type
     * @param id The block ID
     * @return The corresponding BlockData
     */
    static BlockData toBlockData(ModdedBlockType type, int id) {
        switch (type) {
            case NOTE_BLOCK -> {return NoteBlockData.toBlockData(id);}
            case TRIPWIRE -> {return TripwireData.toBlockData(id);}
            default -> throw new IllegalStateException("Unexpected value: " + type.name());
        }
    }

    /**
     * Converts Bukkit BlockData to a custom block ID.
     * All custom block IDs should be positive, including 0.
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

    /**
     * Interface representing NoteBlock data.
     */
    interface NoteBlockData extends ModdedBlockData {

        /**
         * Converts a custom block ID to NoteBlock BlockData.
         *
         * @param id The block ID
         * @return The corresponding NoteBlock BlockData
         */
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

        /**
         * Converts NoteBlock BlockData to a custom block ID.
         *
         * @param data The NoteBlock BlockData
         * @return The block ID
         */
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

    /**
     * Interface representing Tripwire data.
     */
    interface TripwireData extends ModdedBlockData {
        /**
         * Converts a custom block ID to Tripwire BlockData.
         *
         * @param id The block ID
         * @return The corresponding Tripwire BlockData
         */
        static BlockData toBlockData(int id) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        /**
         * Converts Tripwire BlockData to a custom block ID.
         *
         * @param data The Tripwire BlockData
         * @return The block ID
         */
        static int fromBlockData(BlockData data) {
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }
}
