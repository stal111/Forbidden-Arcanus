package com.stal111.forbidden_arcanus.data.server.tags;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.valhelsia.valhelsia_core.datagen.DataProviderContext;
import net.valhelsia.valhelsia_core.datagen.tags.ValhelsiaBlockTagsProvider;
import org.jetbrains.annotations.NotNull;

/**
 * Mod Block Tags Provider <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.server.tags.ModBlockTagsProvider
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-02-11
 */
public class ModBlockTagsProvider extends ValhelsiaBlockTagsProvider {

    public ModBlockTagsProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        this.tag(ModTags.Blocks.FUNGYSS_STEMS).add(ModBlocks.FUNGYSS_STEM.get(), ModBlocks.FUNGYSS_HYPHAE.get());
        this.tag(ModTags.Blocks.MYSTERYWOOD_LOGS).add(ModBlocks.AURUM_LOG.get(), ModBlocks.AURUM_WOOD.get(), ModBlocks.STRIPPED_AURUM_LOG.get(), ModBlocks.STRIPPED_AURUM_WOOD.get());
        this.tag(ModTags.Blocks.EDELWOOD_LOGS).add(ModBlocks.EDELWOOD_LOG.get(), ModBlocks.CARVED_EDELWOOD_LOG.get());
        this.tag(BlockTags.LOGS).addTags(ModTags.Blocks.FUNGYSS_STEMS, ModTags.Blocks.MYSTERYWOOD_LOGS, ModTags.Blocks.EDELWOOD_LOGS);
        this.tag(BlockTags.LOGS_THAT_BURN).addTag(ModTags.Blocks.MYSTERYWOOD_LOGS);
        this.tag(BlockTags.PLANKS).add(ModBlocks.FUNGYSS_PLANKS.get(), ModBlocks.AURUM_PLANKS.get(), ModBlocks.EDELWOOD_PLANKS.get(), ModBlocks.ARCANE_EDELWOOD_PLANKS.get());
        this.tag(BlockTags.WOODEN_SLABS).add(ModBlocks.FUNGYSS_SLAB.get(), ModBlocks.AURUM_SLAB.get(), ModBlocks.EDELWOOD_SLAB.get());
        this.tag(BlockTags.WOODEN_STAIRS).add(ModBlocks.FUNGYSS_STAIRS.get(), ModBlocks.AURUM_STAIRS.get(), ModBlocks.EDELWOOD_STAIRS.get());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.FUNGYSS_PRESSURE_PLATE.get(), ModBlocks.AURUM_PRESSURE_PLATE.get(), ModBlocks.EDELWOOD_PRESSURE_PLATE.get());
        this.tag(BlockTags.BUTTONS).add(ModBlocks.POLISHED_DARKSTONE_BUTTON.get());
        this.tag(BlockTags.WOODEN_BUTTONS).add(ModBlocks.FUNGYSS_BUTTON.get(), ModBlocks.AURUM_BUTTON.get(), ModBlocks.EDELWOOD_BUTTON.get());
        this.tag(BlockTags.WOODEN_FENCES).add(ModBlocks.FUNGYSS_FENCE.get(), ModBlocks.AURUM_FENCE.get(), ModBlocks.EDELWOOD_FENCE.get());
        this.tag(Tags.Blocks.FENCES_WOODEN).add(ModBlocks.FUNGYSS_FENCE.get(), ModBlocks.AURUM_FENCE.get(), ModBlocks.EDELWOOD_FENCE.get());
        this.tag(BlockTags.FENCE_GATES).add(ModBlocks.FUNGYSS_FENCE_GATE.get(), ModBlocks.AURUM_FENCE_GATE.get(), ModBlocks.EDELWOOD_FENCE_GATE.get());
        this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(ModBlocks.FUNGYSS_FENCE_GATE.get(), ModBlocks.AURUM_FENCE_GATE.get(), ModBlocks.EDELWOOD_FENCE_GATE.get());
        this.tag(BlockTags.WOODEN_DOORS).add(ModBlocks.FUNGYSS_DOOR.get(), ModBlocks.AURUM_DOOR.get(), ModBlocks.EDELWOOD_DOOR.get(), ModBlocks.ARCANE_EDELWOOD_DOOR.get());
        this.tag(BlockTags.DOORS).add(ModBlocks.DEORUM_DOOR.get());
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.FUNGYSS_TRAPDOOR.get(), ModBlocks.AURUM_TRAPDOOR.get(), ModBlocks.EDELWOOD_TRAPDOOR.get(), ModBlocks.ARCANE_EDELWOOD_TRAPDOOR.get());
        this.tag(BlockTags.TRAPDOORS).add(ModBlocks.DEORUM_TRAPDOOR.get());
        this.tag(BlockTags.FLOWER_POTS).add(ModBlocks.POTTED_FUNGYSS.get(), ModBlocks.POTTED_AURUM_SAPLING.get(), ModBlocks.POTTED_GROWING_EDELWOOD.get(), ModBlocks.POTTED_YELLOW_ORCHID.get());
        this.tag(ModTags.Blocks.BLACKSMITH_GAVEL_UNAFFECTED);
        this.tag(ModTags.Blocks.MAGICAL_FARMLAND_BLACKLISTED);
        this.tag(ModTags.Blocks.RUNIC_STONES).add(ModBlocks.RUNIC_STONE.get(), ModBlocks.RUNIC_DEEPSLATE.get(), ModBlocks.RUNIC_DARKSTONE.get());
        this.tag(ModTags.Blocks.RUNE_BLOCKS).add(ModBlocks.RUNE_BLOCK.get());
        this.tag(ModTags.Blocks.ARCANE_CRYSTAL_ORES).add(ModBlocks.ARCANE_CRYSTAL_ORE.get(), ModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE.get());
        this.tag(BlockTags.IMPERMEABLE).add(ModBlocks.DEORUM_GLASS.get(), ModBlocks.RUNIC_GLASS.get());
        //TODO
        //this.tag(BlockTags.STANDING_SIGNS).add(ModBlocks.FUNGYSS_SIGN.getFirst().get(), ModBlocks.CHERRY_SIGN.getFirst().get(), ModBlocks.AURUM_SIGN.getFirst().get(), ModBlocks.EDELWOOD_SIGN.getFirst().get());
        //this.tag(BlockTags.WALL_SIGNS).add(ModBlocks.FUNGYSS_SIGN.getSecond().get(), ModBlocks.CHERRY_SIGN.getSecond().get(), ModBlocks.AURUM_SIGN.getSecond().get(), ModBlocks.EDELWOOD_SIGN.getSecond().get());
        this.tag(BlockTags.SAPLINGS).add(ModBlocks.AURUM_SAPLING.get(), ModBlocks.GROWING_EDELWOOD.get());
        this.tag(BlockTags.LEAVES).add(ModBlocks.AURUM_LEAVES.get(), ModBlocks.NUGGETY_AURUM_LEAVES.get());
        this.tag(BlockTags.SMALL_FLOWERS).add(ModBlocks.YELLOW_ORCHID.get());
        this.tag(ModTags.Blocks.DARKSTONE_ORE_REPLACEABLES).add(ModBlocks.DARKSTONE.get());
        this.tag(BlockTags.CROPS).add(ModBlocks.GOLDEN_ORCHID.get());
        this.tag(ModTags.Blocks.HEPHAESTUS_FORGES).add(ModBlocks.HEPHAESTUS_FORGE_TIER_1.get(), ModBlocks.HEPHAESTUS_FORGE_TIER_2.get(), ModBlocks.HEPHAESTUS_FORGE_TIER_3.get(), ModBlocks.HEPHAESTUS_FORGE_TIER_4.get(), ModBlocks.HEPHAESTUS_FORGE_TIER_5.get());

        //TODO
        //this.tag(ValhelsiaTags.Blocks.OFFSET_RENDERING).add(ModBlocks.CHERRY_LEAF_CARPET.get());

        this.tag(ModTags.Blocks.STORAGE_BLOCKS_DEORUM).add(ModBlocks.DEORUM_BLOCK.get());
        this.tag(ModTags.Blocks.STORAGE_BLOCKS_ARCANE_CRYSTAL).add(ModBlocks.ARCANE_CRYSTAL_BLOCK.get());
        this.tag(ModTags.Blocks.STORAGE_BLOCKS_CORRUPTED_ARCANE_CRYSTAL).add(ModBlocks.CORRUPTED_ARCANE_CRYSTAL_BLOCK.get());
        this.tag(ModTags.Blocks.STORAGE_BLOCKS_STELLARITE).add(ModBlocks.STELLARITE_BLOCK.get());
        this.tag(ModTags.Blocks.STORAGE_BLOCKS_OBSIDIANSTEEL).add(ModBlocks.OBSIDIANSTEEL_BLOCK.get());

        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTags(ModTags.Blocks.STORAGE_BLOCKS_DEORUM, ModTags.Blocks.STORAGE_BLOCKS_ARCANE_CRYSTAL, ModTags.Blocks.STORAGE_BLOCKS_CORRUPTED_ARCANE_CRYSTAL, ModTags.Blocks.STORAGE_BLOCKS_STELLARITE, ModTags.Blocks.STORAGE_BLOCKS_OBSIDIANSTEEL);

        this.tag(ModTags.Blocks.ORES_ARCANE_CRYSTAL).add(ModBlocks.ARCANE_CRYSTAL_ORE.get(), ModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE.get());
        this.tag(ModTags.Blocks.ORES_RUNIC).add(ModBlocks.RUNIC_STONE.get(), ModBlocks.RUNIC_DEEPSLATE.get(), ModBlocks.RUNIC_DARKSTONE.get());
        this.tag(ModTags.Blocks.ORES_STELLARITE).add(ModBlocks.STELLA_ARCANUM.get());
        this.tag(Tags.Blocks.ORES).addTag(ModTags.Blocks.ORES_ARCANE_CRYSTAL).addTag(ModTags.Blocks.ORES_RUNIC).addTag(ModTags.Blocks.ORES_STELLARITE);
        this.tag(Tags.Blocks.ORES_IN_GROUND_STONE).add(ModBlocks.ARCANE_CRYSTAL_ORE.get(), ModBlocks.RUNIC_STONE.get(), ModBlocks.STELLA_ARCANUM.get());
        this.tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE).add(ModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE.get(), ModBlocks.RUNIC_DEEPSLATE.get(), ModBlocks.STELLA_ARCANUM.get());
        this.tag(BlockTags.BASE_STONE_OVERWORLD).add(ModBlocks.DARKSTONE.get());

        this.tag(Tags.Blocks.GLASS_BLOCKS).add(ModBlocks.DEORUM_GLASS.get(), ModBlocks.RUNIC_GLASS.get());
        this.tag(Tags.Blocks.GLASS_BLOCKS_COLORLESS).add(ModBlocks.DEORUM_GLASS.get(), ModBlocks.RUNIC_GLASS.get());
        this.tag(Tags.Blocks.GLASS_PANES).add(ModBlocks.DEORUM_GLASS_PANE.get(), ModBlocks.RUNIC_GLASS_PANE.get());
        this.tag(Tags.Blocks.GLASS_PANES_COLORLESS).add(ModBlocks.DEORUM_GLASS_PANE.get(), ModBlocks.RUNIC_GLASS_PANE.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.DEORUM_CHAIN.get(), ModBlocks.STELLA_ARCANUM.get(), ModBlocks.OBSIDIANSTEEL_BLOCK.get(), ModBlocks.DEORUM_BLOCK.get(), ModBlocks.STELLARITE_BLOCK.get(), ModBlocks.SOULLESS_SANDSTONE.get(), ModBlocks.CUT_SOULLESS_SANDSTONE.get(), ModBlocks.POLISHED_SOULLESS_SANDSTONE.get(), ModBlocks.SOULLESS_SANDSTONE_SLAB.get(), ModBlocks.CUT_SOULLESS_SANDSTONE_SLAB.get(), ModBlocks.POLISHED_SOULLESS_SANDSTONE_SLAB.get(), ModBlocks.SOULLESS_SANDSTONE_STAIRS.get(), ModBlocks.POLISHED_SOULLESS_SANDSTONE_STAIRS.get(), ModBlocks.DEORUM_DOOR.get(), ModBlocks.DARKSTONE.get(), ModBlocks.DARKSTONE_SLAB.get(), ModBlocks.DARKSTONE_STAIRS.get(), ModBlocks.POLISHED_DARKSTONE.get(), ModBlocks.POLISHED_DARKSTONE_SLAB.get(), ModBlocks.POLISHED_DARKSTONE_STAIRS.get(), ModBlocks.POLISHED_DARKSTONE_WALL.get(), ModBlocks.POLISHED_DARKSTONE_PRESSURE_PLATE.get(), ModBlocks.POLISHED_DARKSTONE_BUTTON.get(), ModBlocks.CHISELED_POLISHED_DARKSTONE.get(), ModBlocks.GILDED_CHISELED_POLISHED_DARKSTONE.get(), ModBlocks.POLISHED_DARKSTONE_BRICKS.get(), ModBlocks.POLISHED_DARKSTONE_BRICK_SLAB.get(), ModBlocks.POLISHED_DARKSTONE_BRICK_STAIRS.get(), ModBlocks.CRACKED_POLISHED_DARKSTONE_BRICKS.get(), ModBlocks.ARCANE_POLISHED_DARKSTONE.get(), ModBlocks.ARCANE_POLISHED_DARKSTONE_SLAB.get(), ModBlocks.ARCANE_POLISHED_DARKSTONE_STAIRS.get(), ModBlocks.CHISELED_ARCANE_POLISHED_DARKSTONE.get(), ModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR.get(), ModBlocks.DARKSTONE_PEDESTAL.get(), ModBlocks.MAGNETIZED_DARKSTONE_PEDESTAL.get(), ModBlocks.TILED_POLISHED_DARKSTONE_BRICKS.get(), ModBlocks.CLIBANO_CORE.get(), ModBlocks.CLIBANO_SIDE_HORIZONTAL.get(), ModBlocks.CLIBANO_SIDE_VERTICAL.get(), ModBlocks.CLIBANO_CORNER.get(), ModBlocks.CLIBANO_CENTER.get(), ModBlocks.CLIBANO_MAIN_PART.get(), ModBlocks.DEORUM_LANTERN.get(), ModBlocks.DEORUM_SOUL_LANTERN.get())
                .addTag(ModTags.Blocks.RUNIC_STONES).addTag(ModTags.Blocks.RUNE_BLOCKS).addTag(ModTags.Blocks.ARCANE_CRYSTAL_ORES).addTag(ModTags.Blocks.HEPHAESTUS_FORGES);
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.SOULLESS_SAND.get());
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(ModBlocks.AURUM_LEAVES.get(), ModBlocks.NUGGETY_AURUM_LEAVES.get());

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.OBSIDIANSTEEL_BLOCK.get(), ModBlocks.STELLA_ARCANUM.get(), ModBlocks.STELLARITE_BLOCK.get()).addTag(ModTags.Blocks.HEPHAESTUS_FORGES).addTag(ModTags.Blocks.RUNIC_STONES).addTag(ModTags.Blocks.RUNE_BLOCKS);
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.DEORUM_BLOCK.get()).addTag(ModTags.Blocks.ARCANE_CRYSTAL_ORES);
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.DARKSTONE.get(), ModBlocks.DARKSTONE_SLAB.get(), ModBlocks.DARKSTONE_STAIRS.get(), ModBlocks.DARKSTONE_WALL.get(), ModBlocks.POLISHED_DARKSTONE.get(), ModBlocks.POLISHED_DARKSTONE_SLAB.get(), ModBlocks.POLISHED_DARKSTONE_STAIRS.get(), ModBlocks.POLISHED_DARKSTONE_WALL.get(), ModBlocks.POLISHED_DARKSTONE_PRESSURE_PLATE.get(), ModBlocks.POLISHED_DARKSTONE_BUTTON.get(), ModBlocks.CHISELED_POLISHED_DARKSTONE.get(), ModBlocks.GILDED_CHISELED_POLISHED_DARKSTONE.get(), ModBlocks.POLISHED_DARKSTONE_BRICKS.get(), ModBlocks.POLISHED_DARKSTONE_BRICK_SLAB.get(), ModBlocks.POLISHED_DARKSTONE_BRICK_STAIRS.get(), ModBlocks.POLISHED_DARKSTONE_BRICK_WALL.get(), ModBlocks.CRACKED_POLISHED_DARKSTONE_BRICKS.get(), ModBlocks.ARCANE_POLISHED_DARKSTONE.get(), ModBlocks.ARCANE_POLISHED_DARKSTONE_SLAB.get(), ModBlocks.ARCANE_POLISHED_DARKSTONE_STAIRS.get(), ModBlocks.ARCANE_POLISHED_DARKSTONE_WALL.get(), ModBlocks.CHISELED_ARCANE_POLISHED_DARKSTONE.get(), ModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR.get(), ModBlocks.DARKSTONE_PEDESTAL.get(), ModBlocks.MAGNETIZED_DARKSTONE_PEDESTAL.get(), ModBlocks.CLIBANO_CORE.get(), ModBlocks.CLIBANO_SIDE_HORIZONTAL.get(), ModBlocks.CLIBANO_SIDE_VERTICAL.get(), ModBlocks.CLIBANO_CORNER.get(), ModBlocks.CLIBANO_CENTER.get(), ModBlocks.CLIBANO_MAIN_PART.get());
    }
}
