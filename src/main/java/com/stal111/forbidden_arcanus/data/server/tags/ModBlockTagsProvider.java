package com.stal111.forbidden_arcanus.data.server.tags;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
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
        this.tag(ModTags.Blocks.FUNGYSS_STEMS).add(NewModBlocks.FUNGYSS_STEM.get(), NewModBlocks.FUNGYSS_HYPHAE.get());
        this.tag(ModTags.Blocks.CHERRYWOOD_LOGS).add(NewModBlocks.CHERRYWOOD_LOG.get(), NewModBlocks.CHERRYWOOD.get(), NewModBlocks.STRIPPED_CHERRYWOOD_LOG.get(), NewModBlocks.STRIPPED_CHERRYWOOD.get());
        this.tag(ModTags.Blocks.MYSTERYWOOD_LOGS).add(NewModBlocks.MYSTERYWOOD_LOG.get(), NewModBlocks.MYSTERYWOOD.get(), NewModBlocks.STRIPPED_MYSTERYWOOD_LOG.get(), NewModBlocks.STRIPPED_MYSTERYWOOD.get());
        this.tag(ModTags.Blocks.EDELWOOD_LOGS).add(NewModBlocks.EDELWOOD_LOG.get(), NewModBlocks.CARVED_EDELWOOD_LOG.get());
        this.tag(BlockTags.LOGS).addTags(ModTags.Blocks.FUNGYSS_STEMS, ModTags.Blocks.CHERRYWOOD_LOGS, ModTags.Blocks.MYSTERYWOOD_LOGS, ModTags.Blocks.EDELWOOD_LOGS);
        this.tag(BlockTags.PLANKS).add(NewModBlocks.FUNGYSS_PLANKS.get(), NewModBlocks.CHERRYWOOD_PLANKS.get(), NewModBlocks.CARVED_CHERRYWOOD_PLANKS.get(), NewModBlocks.MYSTERYWOOD_PLANKS.get(), NewModBlocks.EDELWOOD_PLANKS.get(), NewModBlocks.ARCANE_EDELWOOD_PLANKS.get());
        this.tag(BlockTags.WOODEN_SLABS).add(NewModBlocks.FUNGYSS_SLAB.get(), NewModBlocks.CHERRYWOOD_SLAB.get(), NewModBlocks.MYSTERYWOOD_SLAB.get(), NewModBlocks.EDELWOOD_SLAB.get());
        this.tag(BlockTags.WOODEN_STAIRS).add(NewModBlocks.FUNGYSS_STAIRS.get(), NewModBlocks.CHERRYWOOD_STAIRS.get(), NewModBlocks.MYSTERYWOOD_STAIRS.get(), NewModBlocks.EDELWOOD_STAIRS.get());
        this.tag(BlockTags.PRESSURE_PLATES).add(NewModBlocks.ARCANE_GOLD_PRESSURE_PLATE.get());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(NewModBlocks.FUNGYSS_PRESSURE_PLATE.get(), NewModBlocks.CHERRYWOOD_PRESSURE_PLATE.get(), NewModBlocks.MYSTERYWOOD_PRESSURE_PLATE.get(), NewModBlocks.EDELWOOD_PRESSURE_PLATE.get());
        this.tag(BlockTags.BUTTONS).add(NewModBlocks.POLISHED_DARKSTONE_BUTTON.get());
        this.tag(BlockTags.WOODEN_BUTTONS).add(NewModBlocks.FUNGYSS_BUTTON.get(), NewModBlocks.CHERRYWOOD_BUTTON.get(), NewModBlocks.MYSTERYWOOD_BUTTON.get(), NewModBlocks.EDELWOOD_BUTTON.get());
        this.tag(BlockTags.WOODEN_FENCES).add(NewModBlocks.FUNGYSS_FENCE.get(), NewModBlocks.CHERRYWOOD_FENCE.get(), NewModBlocks.MYSTERYWOOD_FENCE.get(), NewModBlocks.EDELWOOD_FENCE.get());
        this.tag(Tags.Blocks.FENCES_WOODEN).add(NewModBlocks.FUNGYSS_FENCE.get(), NewModBlocks.CHERRYWOOD_FENCE.get(), NewModBlocks.MYSTERYWOOD_FENCE.get(), NewModBlocks.EDELWOOD_FENCE.get());
        this.tag(BlockTags.FENCE_GATES).add(NewModBlocks.FUNGYSS_FENCE_GATE.get(), NewModBlocks.CHERRYWOOD_FENCE_GATE.get(), NewModBlocks.MYSTERYWOOD_FENCE_GATE.get(), NewModBlocks.EDELWOOD_FENCE_GATE.get());
        this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(NewModBlocks.FUNGYSS_FENCE_GATE.get(), NewModBlocks.CHERRYWOOD_FENCE_GATE.get(), NewModBlocks.MYSTERYWOOD_FENCE_GATE.get(), NewModBlocks.EDELWOOD_FENCE_GATE.get());
        this.tag(BlockTags.WOODEN_DOORS).add(NewModBlocks.FUNGYSS_DOOR.get(), NewModBlocks.CHERRYWOOD_DOOR.get(), NewModBlocks.MYSTERYWOOD_DOOR.get(), NewModBlocks.EDELWOOD_DOOR.get());
        this.tag(BlockTags.DOORS).add(NewModBlocks.ARCANE_GOLD_DOOR.get());
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(NewModBlocks.FUNGYSS_TRAPDOOR.get(), NewModBlocks.CHERRYWOOD_TRAPDOOR.get(), NewModBlocks.MYSTERYWOOD_TRAPDOOR.get(), NewModBlocks.EDELWOOD_TRAPDOOR.get());
        this.tag(BlockTags.TRAPDOORS).add(NewModBlocks.ARCANE_GOLD_TRAPDOOR.get());
        this.tag(BlockTags.CLIMBABLE).add(NewModBlocks.EDELWOOD_LADDER.get());
        this.tag(BlockTags.FLOWER_POTS).add(NewModBlocks.POTTED_CHERRYWOOD_SAPLING.get(), NewModBlocks.POTTED_MYSTERYWOOD_SAPLING.get(), NewModBlocks.POTTED_YELLOW_ORCHID.get());
        this.tag(ModTags.Blocks.BLACKSMITH_GAVEL_UNAFFECTED).add(Blocks.IRON_ORE, Blocks.GOLD_ORE);
        this.tag(ModTags.Blocks.MAGICAL_FARMLAND_BLACKLISTED);
        this.tag(ModTags.Blocks.RUNIC_STONES).add(NewModBlocks.RUNIC_STONE.get(), NewModBlocks.RUNIC_DEEPSLATE.get(), NewModBlocks.RUNIC_DARKSTONE.get());
        this.tag(ModTags.Blocks.RUNE_BLOCKS).add(NewModBlocks.RUNE_BLOCK.get(), NewModBlocks.DARK_RUNE_BLOCK.get());
        this.tag(ModTags.Blocks.ARCANE_CRYSTAL_ORES).add(NewModBlocks.ARCANE_CRYSTAL_ORE.get(), NewModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE.get());
        this.tag(BlockTags.IMPERMEABLE).add(NewModBlocks.ARCANE_GOLDEN_GLASS.get(), NewModBlocks.RUNIC_GLASS.get(), NewModBlocks.DARK_RUNIC_GLASS.get());
        this.tag(BlockTags.STANDING_SIGNS).add(NewModBlocks.FUNGYSS_SIGN.getFirst().get(), NewModBlocks.CHERRYWOOD_SIGN.getFirst().get(), NewModBlocks.MYSTERYWOOD_SIGN.getFirst().get(), NewModBlocks.EDELWOOD_SIGN.getFirst().get());
        this.tag(BlockTags.WALL_SIGNS).add(NewModBlocks.FUNGYSS_SIGN.getSecond().get(), NewModBlocks.CHERRYWOOD_SIGN.getSecond().get(), NewModBlocks.MYSTERYWOOD_SIGN.getSecond().get(), NewModBlocks.EDELWOOD_SIGN.getSecond().get());
        this.tag(BlockTags.SAPLINGS).add(NewModBlocks.CHERRYWOOD_SAPLING.get(), NewModBlocks.MYSTERYWOOD_SAPLING.get());
        this.tag(BlockTags.LEAVES).add(NewModBlocks.CHERRYWOOD_LEAVES.get(), NewModBlocks.MYSTERYWOOD_LEAVES.get(), NewModBlocks.NUGGETY_MYSTERYWOOD_LEAVES.get());
        this.tag(BlockTags.SMALL_FLOWERS).add(NewModBlocks.YELLOW_ORCHID.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(NewModBlocks.ARCANE_GOLDEN_CHAIN.get(), NewModBlocks.PETRIFIED_ROOT.get(), NewModBlocks.DARK_NETHER_STAR_BLOCK.get(), NewModBlocks.STELLA_ARCANUM.get(), NewModBlocks.PROCESSED_OBSIDIAN_BLOCK.get(), NewModBlocks.ARCANE_GOLD_BLOCK.get(), NewModBlocks.STELLARITE_BLOCK.get(), NewModBlocks.XPETRIFIED_ORE.get(), NewModBlocks.ARCANE_CRYSTAL_BLOCK.get(), NewModBlocks.SOULLESS_SANDSTONE.get(), NewModBlocks.CUT_SOULLESS_SANDSTONE.get(), NewModBlocks.POLISHED_SOULLESS_SANDSTONE.get(), NewModBlocks.SOULLESS_SANDSTONE_SLAB.get(), NewModBlocks.CUT_SOULLESS_SANDSTONE_SLAB.get(), NewModBlocks.POLISHED_SOULLESS_SANDSTONE_SLAB.get(), NewModBlocks.SOULLESS_SANDSTONE_STAIRS.get(), NewModBlocks.POLISHED_SOULLESS_SANDSTONE_STAIRS.get(), NewModBlocks.HEPHAESTUS_FORGE.get(), NewModBlocks.ARCANE_GOLD_DOOR.get(), NewModBlocks.ARCANE_GOLD_PRESSURE_PLATE.get(), NewModBlocks.ARCANE_CHISELED_DARKSTONE.get()).addTag(ModTags.Blocks.RUNIC_STONES).addTag(ModTags.Blocks.RUNE_BLOCKS).addTag(ModTags.Blocks.ARCANE_CRYSTAL_ORES);
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(NewModBlocks.SOULLESS_SAND.get());
        this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(NewModBlocks.DARK_NETHER_STAR_BLOCK.get(), NewModBlocks.PROCESSED_OBSIDIAN_BLOCK.get(), NewModBlocks.STELLA_ARCANUM.get(), NewModBlocks.STELLARITE_BLOCK.get(), NewModBlocks.HEPHAESTUS_FORGE.get()).addTag(ModTags.Blocks.RUNIC_STONES).addTag(ModTags.Blocks.RUNE_BLOCKS);
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(NewModBlocks.XPETRIFIED_ORE.get(), NewModBlocks.ARCANE_GOLD_BLOCK.get(), NewModBlocks.ARCANE_CRYSTAL_BLOCK.get()).addTag(ModTags.Blocks.ARCANE_CRYSTAL_ORES);
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(NewModBlocks.ARCANE_CHISELED_DARKSTONE.get());
    }
}