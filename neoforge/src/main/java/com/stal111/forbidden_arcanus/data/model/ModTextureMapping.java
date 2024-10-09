package com.stal111.forbidden_arcanus.data.model;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.properties.ObeliskPart;
import com.stal111.forbidden_arcanus.common.block.properties.clibano.ClibanoCenterType;
import com.stal111.forbidden_arcanus.common.block.properties.clibano.ClibanoSideType;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Map;

/**
 * @author stal111
 * @since 28.10.2023
 */
public class ModTextureMapping {

    private static final String FORBIDDENOMICON = "forbiddenomicon";
    private static final String DESK = "desk";
    private static final String PEDESTAL = "pedestal";
    private static final String CLIBANO = "clibano";
    private static final String HEPHAESTUS_FORGE = "hephaestus_forge";
    private static final String OBELISK = "obelisk";
    private static final String UTREM_JAR = "utrem_jar";
    private static final String QUANTUM_CATCHER = "quantum_catcher";
    private static final String EDELWOOD_LOG = "edelwood_log";

    private static final Map<Integer, ResourceLocation> FORGE_BLOCK_TEXTURES = Map.of(
            1, ResourceLocation.withDefaultNamespace("block/smithing_table_bottom"),
            2, ForbiddenArcanus.location("block/edelwood_planks"),
            3, ForbiddenArcanus.location("block/chiseled_polished_darkstone"),
            4, ForbiddenArcanus.location("block/chiseled_polished_darkstone"),
            5, ForbiddenArcanus.location("block/stellarite_block")
    );

    public static TextureMapping emissiveCube(Block block) {
        return new TextureMapping().put(TextureSlot.ALL, getBlockTexture(block));
    }

    public static TextureMapping emissiveLayerCube(Block block, String folder) {
        return new TextureMapping().put(TextureSlot.ALL, getBlockTexture(block, folder)).put(ModTextureSlots.LAYER, getBlockTexture(block, folder, "_layer"));
    }

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

    public static TextureMapping hephaestusForge(int tier) {
        String folder = HEPHAESTUS_FORGE + "/tier_" + tier;
        return new TextureMapping().put(TextureSlot.TOP, getBlockTexture(folder, "top")).put(ModTextureSlots.TOP_LAYER, getBlockTexture(folder, "top_layer")).put(TextureSlot.SIDE, getBlockTexture(folder, "side")).put(ModTextureSlots.SIDE_LAYER, getBlockTexture(folder, "side_layer")).put(ModTextureSlots.CLOTH_SIDE, getBlockTexture(folder, "cloth_side")).put(TextureSlot.BOTTOM, getBlockTexture(folder, "bottom")).put(ModTextureSlots.BLOCK, FORGE_BLOCK_TEXTURES.get(tier));
    }

    public static TextureMapping obelisk(Block block, ObeliskPart part) {
        return new TextureMapping().put(TextureSlot.TOP, getBlockTexture(block, OBELISK, "_top")).put(TextureSlot.TEXTURE, getBlockTexture(block, OBELISK, "_" + part.getSerializedName()));
    }

    public static TextureMapping utremJar(Block block) {
        return new TextureMapping().put(TextureSlot.SIDE, getBlockTexture(block, UTREM_JAR, "_side")).put(TextureSlot.TOP, getBlockTexture(block, UTREM_JAR, "_top")).put(TextureSlot.BOTTOM, getBlockTexture(block, UTREM_JAR, "_bottom"));
    }

    public static TextureMapping quantumCatcher(String folder) {
        return new TextureMapping().put(TextureSlot.SIDE, ModLocationUtils.getItem(QUANTUM_CATCHER + folder, "quantum_catcher_side")).put(TextureSlot.TOP, ModLocationUtils.getItem(QUANTUM_CATCHER + folder, "quantum_catcher_top")).put(ModTextureSlots.INNER, ModLocationUtils.getItem(QUANTUM_CATCHER, "quantum_catcher_inner"));
    }

    public static TextureMapping edelwoodLog() {
        Block block = ModBlocks.EDELWOOD_LOG.get();

        return new TextureMapping()
                .put(ModTextureSlots.LOG, getBlockTexture(block, EDELWOOD_LOG))
                .put(TextureSlot.TOP, getBlockTexture(block, EDELWOOD_LOG, "_top"))
                .put(TextureSlot.INSIDE, getBlockTexture(block, EDELWOOD_LOG, "_inside"));
    }

    public static TextureMapping edelwoodLogWithFace(boolean leaves) {
        Block block = ModBlocks.EDELWOOD_LOG.get();

        var mapping = new TextureMapping()
                .put(ModTextureSlots.LOG, getBlockTexture(block, EDELWOOD_LOG))
                .put(TextureSlot.TOP, getBlockTexture(block, EDELWOOD_LOG, "_top"))
                .put(TextureSlot.INSIDE, getBlockTexture(block, EDELWOOD_LOG, "_inside"))
                .put(TextureSlot.FRONT, getBlockTexture(block, EDELWOOD_LOG, "_face"));

        if (leaves) {
            mapping.put(ModTextureSlots.LEAVES, getBlockTexture(block, EDELWOOD_LOG, "_leaves"));
        }

        return mapping;
    }

    public static ResourceLocation getBlockTexture(Block block) {
        ResourceLocation resourceLocation = BuiltInRegistries.BLOCK.getKey(block);
        return resourceLocation.withPrefix("block/");
    }

    public static ResourceLocation getBlockTexture(Block block, String folder) {
        return ModLocationUtils.getBlock(block, folder);
    }

    public static ResourceLocation getBlockTexture(Block block, String folder, String suffix) {
        return ModLocationUtils.getBlock(block, folder, suffix);
    }

    public static ResourceLocation getBlockTexture(String folder, String texture) {
        return ForbiddenArcanus.location("block/" + folder + "/" + texture);
    }

    public static ResourceLocation geItemTexture(Holder<Item> item, String folder, String suffix) {
        return ModLocationUtils.getItem(folder, item, suffix);
    }
}
