package com.stal111.forbidden_arcanus.common.block.skull;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.core.ClientAsset;
import net.minecraft.world.level.block.SkullBlock;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 10.09.2023
 */
public enum ObsidianSkullType implements SkullBlock.Type {
    DEFAULT("obsidian_skull", false),
    CRACKED("cracked_obsidian_skull", false),
    FRAGMENTED("fragmented_obsidian_skull", false),
    FADING("fading_obsidian_skull", false),
    AUREALIC("aurealic_obsidian_skull", true),
    ETERNAL("eternal_obsidian_skull", true);

    private final String name;
    private final ClientAsset.ResourceTexture texture;
    private final boolean detailedModel;

    ObsidianSkullType(String name, boolean detailedModel) {
        this.name = name;
        this.texture = ForbiddenArcanus.clientAsset("block/obsidian_skull/" + name);
        this.detailedModel = detailedModel;
        TYPES.put(name, this);
    }

    public ClientAsset.ResourceTexture getTexture() {
        return this.texture;
    }

    public boolean hasDetailedModel() {
        return this.detailedModel;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}
