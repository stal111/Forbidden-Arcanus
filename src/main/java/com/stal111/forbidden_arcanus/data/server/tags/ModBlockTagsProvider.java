package com.stal111.forbidden_arcanus.data.server.tags;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

/**
 * Mod Block Tags Provider <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.server.tags.ModBlockTagsProvider
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-02-11
 */
public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, ForbiddenArcanus.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(ModTags.Blocks.FUNGYSS_STEMS).add(ModBlocks.FUNGYSS_STEM.get(), ModBlocks.FUNGYSS_HYPHAE.get());
        this.tag(ModTags.Blocks.CHERRYWOOD_LOGS).add(ModBlocks.CHERRYWOOD_LOG.get(), ModBlocks.CHERRYWOOD.get(), ModBlocks.STRIPPED_CHERRYWOOD_LOG.get(), ModBlocks.STRIPPED_CHERRYWOOD.get());
        this.tag(ModTags.Blocks.MYSTERYWOOD_LOGS).add(ModBlocks.MYSTERYWOOD_LOG.get(), ModBlocks.MYSTERYWOOD.get(), ModBlocks.STRIPPED_MYSTERYWOOD_LOG.get(), ModBlocks.STRIPPED_MYSTERYWOOD.get());
        this.tag(ModTags.Blocks.EDELWOOD_LOGS).add(ModBlocks.EDELWOOD_LOG.get(), ModBlocks.CARVED_EDELWOOD_LOG.get());
        this.tag(BlockTags.LOGS).addTags(ModTags.Blocks.FUNGYSS_STEMS, ModTags.Blocks.CHERRYWOOD_LOGS, ModTags.Blocks.MYSTERYWOOD_LOGS, ModTags.Blocks.EDELWOOD_LOGS);
        this.tag(BlockTags.LOGS_THAT_BURN).addTag(ModTags.Blocks.CHERRYWOOD_LOGS).addTag(ModTags.Blocks.MYSTERYWOOD_LOGS).addTag(ModTags.Blocks.EDELWOOD_LOGS);
        this.tag(BlockTags.PLANKS).add(ModBlocks.FUNGYSS_PLANKS.get(), ModBlocks.CHERRYWOOD_PLANKS.get(), ModBlocks.CARVED_CHERRYWOOD_PLANKS.get(), ModBlocks.MYSTERYWOOD_PLANKS.get(), ModBlocks.EDELWOOD_PLANKS.get(), ModBlocks.ARCANE_EDELWOOD_PLANKS.get());
        this.tag(BlockTags.WOODEN_SLABS).add(ModBlocks.FUNGYSS_SLAB.get(), ModBlocks.CHERRYWOOD_SLAB.get(), ModBlocks.MYSTERYWOOD_SLAB.get(), ModBlocks.EDELWOOD_SLAB.get());
        this.tag(BlockTags.WOODEN_STAIRS).add(ModBlocks.FUNGYSS_STAIRS.get(), ModBlocks.CHERRYWOOD_STAIRS.get(), ModBlocks.MYSTERYWOOD_STAIRS.get(), ModBlocks.EDELWOOD_STAIRS.get());
        this.tag(BlockTags.PRESSURE_PLATES).add(ModBlocks.ARCANE_GOLD_PRESSURE_PLATE.get());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.FUNGYSS_PRESSURE_PLATE.get(), ModBlocks.CHERRYWOOD_PRESSURE_PLATE.get(), ModBlocks.MYSTERYWOOD_PRESSURE_PLATE.get(), ModBlocks.EDELWOOD_PRESSURE_PLATE.get());
        this.tag(BlockTags.BUTTONS).add(ModBlocks.POLISHED_DARKSTONE_BUTTON.get());
        this.tag(BlockTags.WOODEN_BUTTONS).add(ModBlocks.FUNGYSS_BUTTON.get(), ModBlocks.CHERRYWOOD_BUTTON.get(), ModBlocks.MYSTERYWOOD_BUTTON.get(), ModBlocks.EDELWOOD_BUTTON.get());
        this.tag(BlockTags.WOODEN_FENCES).add(ModBlocks.FUNGYSS_FENCE.get(), ModBlocks.CHERRYWOOD_FENCE.get(), ModBlocks.MYSTERYWOOD_FENCE.get(), ModBlocks.EDELWOOD_FENCE.get());
        this.tag(Tags.Blocks.FENCES_WOODEN).add(ModBlocks.FUNGYSS_FENCE.get(), ModBlocks.CHERRYWOOD_FENCE.get(), ModBlocks.MYSTERYWOOD_FENCE.get(), ModBlocks.EDELWOOD_FENCE.get());
        this.tag(BlockTags.FENCE_GATES).add(ModBlocks.FUNGYSS_FENCE_GATE.get(), ModBlocks.CHERRYWOOD_FENCE_GATE.get(), ModBlocks.MYSTERYWOOD_FENCE_GATE.get(), ModBlocks.EDELWOOD_FENCE_GATE.get());
        this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(ModBlocks.FUNGYSS_FENCE_GATE.get(), ModBlocks.CHERRYWOOD_FENCE_GATE.get(), ModBlocks.MYSTERYWOOD_FENCE_GATE.get(), ModBlocks.EDELWOOD_FENCE_GATE.get());
        this.tag(BlockTags.WOODEN_DOORS).add(ModBlocks.FUNGYSS_DOOR.get(), ModBlocks.CHERRYWOOD_DOOR.get(), ModBlocks.MYSTERYWOOD_DOOR.get(), ModBlocks.EDELWOOD_DOOR.get());
        this.tag(BlockTags.DOORS).add(ModBlocks.ARCANE_GOLD_DOOR.get());
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.FUNGYSS_TRAPDOOR.get(), ModBlocks.CHERRYWOOD_TRAPDOOR.get(), ModBlocks.MYSTERYWOOD_TRAPDOOR.get(), ModBlocks.EDELWOOD_TRAPDOOR.get());
        this.tag(BlockTags.TRAPDOORS).add(ModBlocks.ARCANE_GOLD_TRAPDOOR.get());
        this.tag(BlockTags.CLIMBABLE).add(ModBlocks.EDELWOOD_LADDER.get());
        this.tag(BlockTags.FLOWER_POTS).add(ModBlocks.POTTED_CHERRYWOOD_SAPLING.get(), ModBlocks.POTTED_MYSTERYWOOD_SAPLING.get(), ModBlocks.POTTED_YELLOW_ORCHID.get());
        this.tag(ModTags.Blocks.BLACKSMITH_GAVEL_UNAFFECTED).add(Blocks.IRON_ORE, Blocks.GOLD_ORE);
        this.tag(ModTags.Blocks.MAGICAL_FARMLAND_BLACKLISTED);
        this.tag(ModTags.Blocks.RUNIC_STONES).add(ModBlocks.RUNIC_STONE.get(), ModBlocks.RUNIC_DEEPSLATE.get(), ModBlocks.RUNIC_DARKSTONE.get());
        this.tag(ModTags.Blocks.RUNE_BLOCKS).add(ModBlocks.RUNE_BLOCK.get(), ModBlocks.DARK_RUNE_BLOCK.get());
        this.tag(ModTags.Blocks.ARCANE_CRYSTAL_ORES).add(ModBlocks.ARCANE_CRYSTAL_ORE.get(), ModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE.get());
        this.tag(BlockTags.IMPERMEABLE).add(ModBlocks.ARCANE_GOLDEN_GLASS.get(), ModBlocks.RUNIC_GLASS.get(), ModBlocks.DARK_RUNIC_GLASS.get());
        this.tag(BlockTags.STANDING_SIGNS).add(ModBlocks.FUNGYSS_SIGN.getFirst().get(), ModBlocks.CHERRYWOOD_SIGN.getFirst().get(), ModBlocks.MYSTERYWOOD_SIGN.getFirst().get(), ModBlocks.EDELWOOD_SIGN.getFirst().get());
        this.tag(BlockTags.WALL_SIGNS).add(ModBlocks.FUNGYSS_SIGN.getSecond().get(), ModBlocks.CHERRYWOOD_SIGN.getSecond().get(), ModBlocks.MYSTERYWOOD_SIGN.getSecond().get(), ModBlocks.EDELWOOD_SIGN.getSecond().get());
        this.tag(BlockTags.SAPLINGS).add(ModBlocks.CHERRYWOOD_SAPLING.get(), ModBlocks.MYSTERYWOOD_SAPLING.get());
        this.tag(BlockTags.LEAVES).add(ModBlocks.CHERRYWOOD_LEAVES.get(), ModBlocks.MYSTERYWOOD_LEAVES.get(), ModBlocks.NUGGETY_MYSTERYWOOD_LEAVES.get());
        this.tag(BlockTags.SMALL_FLOWERS).add(ModBlocks.YELLOW_ORCHID.get());
        this.tag(ModTags.Blocks.DARKSTONE_ORE_REPLACEABLES).add(ModBlocks.DARKSTONE.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.ARCANE_GOLDEN_CHAIN.get(), ModBlocks.PETRIFIED_ROOT.get(), ModBlocks.DARK_NETHER_STAR_BLOCK.get(), ModBlocks.STELLA_ARCANUM.get(), ModBlocks.PROCESSED_OBSIDIAN_BLOCK.get(), ModBlocks.ARCANE_GOLD_BLOCK.get(), ModBlocks.STELLARITE_BLOCK.get(), ModBlocks.XPETRIFIED_ORE.get(), ModBlocks.ARCANE_CRYSTAL_BLOCK.get(), ModBlocks.SOULLESS_SANDSTONE.get(), ModBlocks.CUT_SOULLESS_SANDSTONE.get(), ModBlocks.POLISHED_SOULLESS_SANDSTONE.get(), ModBlocks.SOULLESS_SANDSTONE_SLAB.get(), ModBlocks.CUT_SOULLESS_SANDSTONE_SLAB.get(), ModBlocks.POLISHED_SOULLESS_SANDSTONE_SLAB.get(), ModBlocks.SOULLESS_SANDSTONE_STAIRS.get(), ModBlocks.POLISHED_SOULLESS_SANDSTONE_STAIRS.get(), ModBlocks.HEPHAESTUS_FORGE.get(), ModBlocks.ARCANE_GOLD_DOOR.get(), ModBlocks.ARCANE_GOLD_PRESSURE_PLATE.get(), ModBlocks.DARKSTONE.get(), ModBlocks.ARCANE_CHISELED_DARKSTONE.get(), ModBlocks.DARKSTONE_SLAB.get(), ModBlocks.DARKSTONE_STAIRS.get(), ModBlocks.ARCANE_GILDED_DARKSTONE.get(), ModBlocks.POLISHED_DARKSTONE.get(), ModBlocks.POLISHED_DARKSTONE_SLAB.get(), ModBlocks.POLISHED_DARKSTONE_STAIRS.get(), ModBlocks.POLISHED_DARKSTONE_PRESSURE_PLATE.get(), ModBlocks.POLISHED_DARKSTONE_BUTTON.get(), ModBlocks.CHISELED_POLISHED_DARKSTONE.get(), ModBlocks.RUNIC_CHISELED_POLISHED_DARKSTONE.get(), ModBlocks.ARCANE_CHISELED_POLISHED_DARKSTONE.get(), ModBlocks.POLISHED_DARKSTONE_BRICKS.get(), ModBlocks.POLISHED_DARKSTONE_BRICK_SLAB.get(), ModBlocks.POLISHED_DARKSTONE_BRICK_STAIRS.get(), ModBlocks.CRACKED_POLISHED_DARKSTONE_BRICKS.get(), ModBlocks.ARCANE_POLISHED_DARKSTONE.get(), ModBlocks.ARCANE_POLISHED_DARKSTONE_SLAB.get(), ModBlocks.ARCANE_POLISHED_DARKSTONE_STAIRS.get(), ModBlocks.CHISELED_ARCANE_POLISHED_DARKSTONE.get(), ModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR.get(), ModBlocks.ARCANE_POLISHED_DARKSTONE_ROD.get(), ModBlocks.DARKSTONE_PEDESTAL.get(), ModBlocks.ARCANE_DARKSTONE_PEDESTAL.get())
                .addTag(ModTags.Blocks.RUNIC_STONES).addTag(ModTags.Blocks.RUNE_BLOCKS).addTag(ModTags.Blocks.ARCANE_CRYSTAL_ORES);
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.SOULLESS_SAND.get());

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.DARK_NETHER_STAR_BLOCK.get(), ModBlocks.PROCESSED_OBSIDIAN_BLOCK.get(), ModBlocks.STELLA_ARCANUM.get(), ModBlocks.STELLARITE_BLOCK.get(), ModBlocks.HEPHAESTUS_FORGE.get()).addTag(ModTags.Blocks.RUNIC_STONES).addTag(ModTags.Blocks.RUNE_BLOCKS);
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.XPETRIFIED_ORE.get(), ModBlocks.ARCANE_GOLD_BLOCK.get(), ModBlocks.ARCANE_CRYSTAL_BLOCK.get()).addTag(ModTags.Blocks.ARCANE_CRYSTAL_ORES);
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.DARKSTONE.get(), ModBlocks.ARCANE_CHISELED_DARKSTONE.get(), ModBlocks.DARKSTONE_SLAB.get(), ModBlocks.DARKSTONE_STAIRS.get(), ModBlocks.DARKSTONE_WALL.get(), ModBlocks.ARCANE_GILDED_DARKSTONE.get(), ModBlocks.POLISHED_DARKSTONE.get(), ModBlocks.POLISHED_DARKSTONE_SLAB.get(), ModBlocks.POLISHED_DARKSTONE_STAIRS.get(), ModBlocks.POLISHED_DARKSTONE_WALL.get(), ModBlocks.POLISHED_DARKSTONE_PRESSURE_PLATE.get(), ModBlocks.POLISHED_DARKSTONE_BUTTON.get(), ModBlocks.CHISELED_POLISHED_DARKSTONE.get(), ModBlocks.RUNIC_CHISELED_POLISHED_DARKSTONE.get(), ModBlocks.ARCANE_CHISELED_POLISHED_DARKSTONE.get(), ModBlocks.POLISHED_DARKSTONE_BRICKS.get(), ModBlocks.POLISHED_DARKSTONE_BRICK_SLAB.get(), ModBlocks.POLISHED_DARKSTONE_BRICK_STAIRS.get(), ModBlocks.POLISHED_DARKSTONE_BRICK_WALL.get(), ModBlocks.CRACKED_POLISHED_DARKSTONE_BRICKS.get(), ModBlocks.ARCANE_POLISHED_DARKSTONE.get(), ModBlocks.ARCANE_POLISHED_DARKSTONE_SLAB.get(), ModBlocks.ARCANE_POLISHED_DARKSTONE_STAIRS.get(), ModBlocks.ARCANE_POLISHED_DARKSTONE_WALL.get(), ModBlocks.CHISELED_ARCANE_POLISHED_DARKSTONE.get(), ModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR.get(), ModBlocks.ARCANE_POLISHED_DARKSTONE_ROD.get(), ModBlocks.DARKSTONE_PEDESTAL.get(), ModBlocks.ARCANE_DARKSTONE_PEDESTAL.get());
    }
}