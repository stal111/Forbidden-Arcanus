package com.stal111.forbidden_arcanus.data.model;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.properties.clibano.ClibanoCenterType;
import com.stal111.forbidden_arcanus.common.block.properties.clibano.ClibanoSideType;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

/**
 * @author stal111
 * @since 28.10.2023
 */
public class ModTextureMapping {

    private static final String FORBIDDENOMICON = "forbiddenomicon";
    private static final String DESK = "desk";
    private static final String PEDESTAL = "pedestal";
    private static final String CLIBANO = "clibano";

    public static TextureMapping forbiddenomicon(Block block) {
        return new TextureMapping().put(TextureSlot.FRONT, getBlockTexture(block, FORBIDDENOMICON, "_front")).put(TextureSlot.BACK, getBlockTexture(block, FORBIDDENOMICON, "_back")).put(TextureSlot.INSIDE, getBlockTexture(block, FORBIDDENOMICON, "_inside")).put(TextureSlot.SIDE, getBlockTexture(block, FORBIDDENOMICON, "_side"));
    }

    public static TextureMapping desk(boolean research) {
        Block desk = ModBlocks.DESK.get();
        Block researchDesk = ModBlocks.RESEARCH_DESK.get();

        return new TextureMapping().put(TextureSlot.FRONT, getBlockTexture(desk, DESK, "_front")).put(TextureSlot.BACK, getBlockTexture(research ? researchDesk : desk, DESK, "_back")).put(TextureSlot.INSIDE, getBlockTexture(desk, DESK, "_inside")).put(TextureSlot.SIDE, getBlockTexture(research ? researchDesk : desk, DESK, "_side")).put(TextureSlot.TOP, getBlockTexture(desk, DESK, "_top")).put(TextureSlot.BOTTOM, getBlockTexture(desk, DESK, "_bottom"));
    }

    public static TextureMapping pedestal(Block block) {
        return new TextureMapping().put(TextureSlot.SIDE, getBlockTexture(block, PEDESTAL)).put(TextureSlot.TOP, getBlockTexture(block, PEDESTAL, "_top"));
    }

    public static TextureMapping clibanoCore() {
        return new TextureMapping().put(TextureSlot.SIDE, getBlockTexture(CLIBANO, "clibano_center_side")).put(TextureSlot.FRONT, getBlockTexture(CLIBANO, "clibano_center_front_off")).put(TextureSlot.TOP, getBlockTexture(CLIBANO, "clibano_center_top"));
    }

    public static TextureMapping clibanoCenter(ClibanoCenterType type) {
        return new TextureMapping().put(TextureSlot.TEXTURE, getBlockTexture(CLIBANO, type.getFireType() != null ? type.getFireType().getSerializedName() + "/clibano_center_front" : "clibano_center_" + type.getSerializedName()));
    }

    public static TextureMapping clibanoSide(ClibanoSideType type) {
        return new TextureMapping().put(TextureSlot.SIDE, getBlockTexture(CLIBANO, type == ClibanoSideType.OFF ? "clibano_side_off" : type.getSerializedName() + "/clibano_side"));
    }

    public static ResourceLocation getBlockTexture(Block block) {
        ResourceLocation resourceLocation = BuiltInRegistries.BLOCK.getKey(block);
        return resourceLocation.withPrefix("block/");
    }

    public static ResourceLocation getBlockTexture(Block block, String folder) {
        ResourceLocation resourceLocation = BuiltInRegistries.BLOCK.getKey(block);
        return resourceLocation.withPrefix("block/" + folder + "/");
    }

    public static ResourceLocation getBlockTexture(Block block, String folder, String suffix) {
        ResourceLocation resourceLocation = BuiltInRegistries.BLOCK.getKey(block);
        return resourceLocation.withPath(s -> "block/" + folder + "/" + s + suffix);
    }

    public static ResourceLocation getBlockTexture(String folder, String texture) {
        return new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/" + folder + "/" + texture);
    }
}
