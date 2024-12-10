package rip.diamond.moddedbukkit.block;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExampleBlock {

    DIAMOND_DIRT(DiamondDirtBlock.class),
    ;

    private final Class<? extends ModdedBlock> clazz;

}
