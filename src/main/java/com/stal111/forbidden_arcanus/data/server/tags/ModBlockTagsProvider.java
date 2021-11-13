package com.stal111.forbidden_arcanus.data.server.tags;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
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
        this.tag(BlockTags.LOGS).addTags(ModTags.Blocks.FUNGYSS_STEMS, ModTags.Blocks.CHERRYWOOD_LOGS, ModTags.Blocks.MYSTERYWOOD_LOGS).add(ModBlocks.EDELWOOD_LOG.getBlock());
        this.tag(BlockTags.PLANKS).add(NewModBlocks.FUNGYSS_PLANKS.get(), ModBlocks.CHERRYWOOD_PLANKS.getBlock(), ModBlocks.CARVED_CHERRYWOOD_PLANKS.getBlock(), ModBlocks.MYSTERYWOOD_PLANKS.getBlock(), ModBlocks.EDELWOOD_PLANKS.getBlock(), ModBlocks.ARCANE_EDELWOOD_PLANKS.getBlock());
        this.tag(BlockTags.WOODEN_SLABS).add(NewModBlocks.FUNGYSS_SLAB.get(), ModBlocks.CHERRYWOOD_SLAB.getBlock(), ModBlocks.MYSTERYWOOD_SLAB.getBlock(), ModBlocks.EDELWOOD_SLAB.getBlock());
        this.tag(BlockTags.WOODEN_STAIRS).add(NewModBlocks.FUNGYSS_STAIRS.get(), ModBlocks.CHERRYWOOD_STAIRS.getBlock(), ModBlocks.MYSTERYWOOD_STAIRS.getBlock(), ModBlocks.EDELWOOD_STAIRS.getBlock());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(NewModBlocks.FUNGYSS_PRESSURE_PLATE.get(), ModBlocks.CHERRYWOOD_PRESSURE_PLATE.getBlock(), ModBlocks.MYSTERYWOOD_PRESSURE_PLATE.getBlock(), ModBlocks.EDELWOOD_PRESSURE_PLATE.getBlock());
        this.tag(BlockTags.WOODEN_BUTTONS).add(NewModBlocks.FUNGYSS_BUTTON.get(), ModBlocks.CHERRYWOOD_BUTTON.getBlock(), ModBlocks.MYSTERYWOOD_BUTTON.getBlock(), ModBlocks.EDELWOOD_BUTTON.getBlock());
        this.tag(BlockTags.WOODEN_FENCES).add(NewModBlocks.FUNGYSS_FENCE.get(), ModBlocks.CHERRYWOOD_FENCE.getBlock(), ModBlocks.MYSTERYWOOD_FENCE.getBlock(), ModBlocks.EDELWOOD_FENCE.getBlock());
        this.tag(Tags.Blocks.FENCES_WOODEN).add(NewModBlocks.FUNGYSS_FENCE.get(), ModBlocks.CHERRYWOOD_FENCE.getBlock(), ModBlocks.MYSTERYWOOD_FENCE.getBlock(), ModBlocks.EDELWOOD_FENCE.getBlock());
        this.tag(BlockTags.FENCE_GATES).add(NewModBlocks.FUNGYSS_FENCE_GATE.get(), ModBlocks.CHERRYWOOD_FENCE_GATE.getBlock(), ModBlocks.MYSTERYWOOD_FENCE_GATE.getBlock(), ModBlocks.EDELWOOD_FENCE_GATE.getBlock());
        this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(NewModBlocks.FUNGYSS_FENCE_GATE.get(), ModBlocks.CHERRYWOOD_FENCE_GATE.getBlock(), ModBlocks.MYSTERYWOOD_FENCE_GATE.getBlock(), ModBlocks.EDELWOOD_FENCE_GATE.getBlock());
        this.tag(BlockTags.WOODEN_DOORS).add(NewModBlocks.FUNGYSS_DOOR.get(), ModBlocks.CHERRYWOOD_DOOR.getBlock(), ModBlocks.MYSTERYWOOD_DOOR.getBlock(), ModBlocks.EDELWOOD_DOOR.getBlock());
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(NewModBlocks.FUNGYSS_TRAPDOOR.get(), ModBlocks.CHERRYWOOD_TRAPDOOR.getBlock(), ModBlocks.MYSTERYWOOD_TRAPDOOR.getBlock(), ModBlocks.EDELWOOD_TRAPDOOR.getBlock());
        this.tag(BlockTags.CLIMBABLE).add(ModBlocks.EDELWOOD_LADDER.getBlock());
        this.tag(BlockTags.FLOWER_POTS).add(NewModBlocks.POTTED_CHERRYWOOD_SAPLING.get(), NewModBlocks.POTTED_MYSTERYWOOD_SAPLING.get(), NewModBlocks.POTTED_YELLOW_ORCHID.get());
        this.tag(ModTags.Blocks.BLACKSMITH_GAVEL_UNAFFECTED).add(Blocks.IRON_ORE, Blocks.GOLD_ORE);
        this.tag(ModTags.Blocks.MAGICAL_FARMLAND_BLACKLISTED);
        this.tag(ModTags.Blocks.RUNIC_STONES).add(NewModBlocks.RUNIC_STONE.get(), NewModBlocks.RUNIC_DEEPSLATE.get(), NewModBlocks.RUNIC_DARKSTONE.get());
        this.tag(ModTags.Blocks.RUNE_BLOCKS).add(NewModBlocks.RUNE_BLOCK.get(), NewModBlocks.DARK_RUNE_BLOCK.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).addTag(ModTags.Blocks.RUNIC_STONES).addTag(ModTags.Blocks.RUNE_BLOCKS);
        this.tag(BlockTags.NEEDS_DIAMOND_TOOL).addTag(ModTags.Blocks.RUNIC_STONES).addTag(ModTags.Blocks.RUNE_BLOCKS);
    }
}