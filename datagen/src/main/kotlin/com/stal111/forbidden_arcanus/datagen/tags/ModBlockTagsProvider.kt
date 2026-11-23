package com.stal111.forbidden_arcanus.datagen.tags

import com.stal111.forbidden_arcanus.core.init.ModBlocks
import com.stal111.forbidden_arcanus.util.ModTags
import net.minecraft.core.HolderLookup
import net.minecraft.tags.BlockTags
import net.neoforged.neoforge.common.Tags
import net.valhelsia.dataforge.DataProviderContext
import net.valhelsia.dataforge.tag.DataForgeBlockTagsProvider

class ModBlockTagsProvider(context: DataProviderContext.Server) : DataForgeBlockTagsProvider(context) {
    override fun addTags(provider: HolderLookup.Provider) {
        this.tag(ModTags.Blocks.FUNGYSS_STEMS).add(ModBlocks.FUNGYSS_STEM.key(), ModBlocks.FUNGYSS_HYPHAE.key())
        this.tag(ModTags.Blocks.MYSTERYWOOD_LOGS).add(
            ModBlocks.AURUM_LOG.key(),
            ModBlocks.AURUM_WOOD.key(),
            ModBlocks.STRIPPED_AURUM_LOG.key(),
            ModBlocks.STRIPPED_AURUM_WOOD.key()
        )
        this.tag(ModTags.Blocks.EDELWOOD_LOGS).add(ModBlocks.EDELWOOD_LOG.key(), ModBlocks.CARVED_EDELWOOD_LOG.key())
        this.tag(BlockTags.LOGS)
            .addTags(ModTags.Blocks.FUNGYSS_STEMS, ModTags.Blocks.MYSTERYWOOD_LOGS, ModTags.Blocks.EDELWOOD_LOGS)
//        this.tag(BlockTags.LOGS_THAT_BURN).addTag(ModTags.Blocks.MYSTERYWOOD_LOGS)
        this.tag(BlockTags.PLANKS).add(
            ModBlocks.FUNGYSS_PLANKS.key(),
            ModBlocks.AURUM_PLANKS.key(),
            ModBlocks.EDELWOOD_PLANKS.key(),
            ModBlocks.ARCANE_EDELWOOD_PLANKS.key()
        )
        this.tag(BlockTags.WOODEN_SLABS)
            .add(ModBlocks.FUNGYSS_SLAB.key(), ModBlocks.AURUM_SLAB.key(), ModBlocks.EDELWOOD_SLAB.key())
        this.tag(BlockTags.WOODEN_STAIRS)
            .add(ModBlocks.FUNGYSS_STAIRS.key(), ModBlocks.AURUM_STAIRS.key(), ModBlocks.EDELWOOD_STAIRS.key())
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(
            ModBlocks.FUNGYSS_PRESSURE_PLATE.key(),
            ModBlocks.AURUM_PRESSURE_PLATE.key(),
            ModBlocks.EDELWOOD_PRESSURE_PLATE.key()
        )
        this.tag(BlockTags.BUTTONS).add(ModBlocks.POLISHED_DARKSTONE_BUTTON.key())
        this.tag(BlockTags.WOODEN_BUTTONS)
            .add(ModBlocks.FUNGYSS_BUTTON.key(), ModBlocks.AURUM_BUTTON.key(), ModBlocks.EDELWOOD_BUTTON.key())
        this.tag(BlockTags.WOODEN_FENCES)
            .add(ModBlocks.FUNGYSS_FENCE.key(), ModBlocks.AURUM_FENCE.key(), ModBlocks.EDELWOOD_FENCE.key())
        this.tag(Tags.Blocks.FENCES_WOODEN)
            .add(ModBlocks.FUNGYSS_FENCE.key(), ModBlocks.AURUM_FENCE.key(), ModBlocks.EDELWOOD_FENCE.key())
        this.tag(BlockTags.FENCE_GATES).add(
            ModBlocks.FUNGYSS_FENCE_GATE.key(),
            ModBlocks.AURUM_FENCE_GATE.key(),
            ModBlocks.EDELWOOD_FENCE_GATE.key()
        )
        this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(
            ModBlocks.FUNGYSS_FENCE_GATE.key(),
            ModBlocks.AURUM_FENCE_GATE.key(),
            ModBlocks.EDELWOOD_FENCE_GATE.key()
        )
        this.tag(BlockTags.WALLS).add(
            ModBlocks.DARKSTONE_WALL.key(),
            ModBlocks.POLISHED_DARKSTONE_WALL.key(),
            ModBlocks.POLISHED_DARKSTONE_BRICK_WALL.key(),
            ModBlocks.SOULLESS_SANDSTONE_WALL.key(),
            ModBlocks.ARCANE_POLISHED_DARKSTONE_WALL.key(),
        )
        this.tag(BlockTags.WOODEN_DOORS).add(
            ModBlocks.FUNGYSS_DOOR.key(),
            ModBlocks.AURUM_DOOR.key(),
            ModBlocks.EDELWOOD_DOOR.key(),
            ModBlocks.ARCANE_EDELWOOD_DOOR.key()
        )
        this.tag(BlockTags.DOORS).add(ModBlocks.DEORUM_DOOR.key())
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(
            ModBlocks.FUNGYSS_TRAPDOOR.key(),
            ModBlocks.AURUM_TRAPDOOR.key(),
            ModBlocks.EDELWOOD_TRAPDOOR.key(),
            ModBlocks.ARCANE_EDELWOOD_TRAPDOOR.key()
        )
        this.tag(BlockTags.TRAPDOORS).add(ModBlocks.DEORUM_TRAPDOOR.key())
        this.tag(BlockTags.FLOWER_POTS).add(
            ModBlocks.POTTED_FUNGYSS.key(),
            ModBlocks.POTTED_AURUM_SAPLING.key(),
            ModBlocks.POTTED_GROWING_EDELWOOD.key(),
            ModBlocks.POTTED_YELLOW_ORCHID.key()
        )
        this.tag(ModTags.Blocks.BLACKSMITH_GAVEL_UNAFFECTED)
        this.tag(ModTags.Blocks.MAGICAL_FARMLAND_BLACKLISTED)
        this.tag(ModTags.Blocks.ARCANE_BONE_MEAL_BLACKLISTED)
        this.tag(ModTags.Blocks.ARCANE_BONE_MEAL_SINGLE_USE)
        this.tag(ModTags.Blocks.RUNIC_STONES)
            .add(ModBlocks.RUNIC_STONE.key(), ModBlocks.RUNIC_DEEPSLATE.key(), ModBlocks.RUNIC_DARKSTONE.key())
        this.tag(ModTags.Blocks.RUNE_BLOCKS).add(ModBlocks.RUNE_BLOCK.key())
        this.tag(ModTags.Blocks.ARCANE_CRYSTAL_ORES)
            .add(ModBlocks.ARCANE_CRYSTAL_ORE.key(), ModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE.key())
        this.tag(BlockTags.IMPERMEABLE).add(ModBlocks.DEORUM_GLASS.key(), ModBlocks.RUNIC_GLASS.key())
        //TODO
        //this.tag(BlockTags.STANDING_SIGNS).add(ModBlocks.FUNGYSS_SIGN.getFirst().key(), ModBlocks.CHERRY_SIGN.getFirst().key(), ModBlocks.AURUM_SIGN.getFirst().key(), ModBlocks.EDELWOOD_SIGN.getFirst().key());
        //this.tag(BlockTags.WALL_SIGNS).add(ModBlocks.FUNGYSS_SIGN.getSecond().key(), ModBlocks.CHERRY_SIGN.getSecond().key(), ModBlocks.AURUM_SIGN.getSecond().key(), ModBlocks.EDELWOOD_SIGN.getSecond().key());
//        this.tag(BlockTags.SAPLINGS).add(ModBlocks.AURUM_SAPLING.key(), ModBlocks.GROWING_EDELWOOD.key())
        this.tag(BlockTags.LEAVES).add(ModBlocks.AURUM_LEAVES.key(), ModBlocks.NUGGETY_AURUM_LEAVES.key())
        this.tag(BlockTags.SMALL_FLOWERS).add(ModBlocks.YELLOW_ORCHID.key())
        this.tag(ModTags.Blocks.DARKSTONE_ORE_REPLACEABLES).add(ModBlocks.DARKSTONE.key())
        this.tag(ModTags.Blocks.PEDESTALS)
            .add(ModBlocks.DARKSTONE_PEDESTAL.key(), ModBlocks.MAGNETIZED_DARKSTONE_PEDESTAL.key())

        this.tag(ModTags.Blocks.STORAGE_BLOCKS_DEORUM).add(ModBlocks.DEORUM_BLOCK.key())
        this.tag(ModTags.Blocks.STORAGE_BLOCKS_ARCANE_CRYSTAL).add(ModBlocks.ARCANE_CRYSTAL_BLOCK.key())
        this.tag(ModTags.Blocks.STORAGE_BLOCKS_CORRUPTED_ARCANE_CRYSTAL)
            .add(ModBlocks.CORRUPTED_ARCANE_CRYSTAL_BLOCK.key())
        this.tag(ModTags.Blocks.STORAGE_BLOCKS_STELLARITE).add(ModBlocks.STELLARITE_BLOCK.key())
        this.tag(ModTags.Blocks.STORAGE_BLOCKS_STYGIAN_STEEL).add(ModBlocks.STYGIAN_STEEL_BLOCK.key())

        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTags(
            ModTags.Blocks.STORAGE_BLOCKS_DEORUM,
            ModTags.Blocks.STORAGE_BLOCKS_ARCANE_CRYSTAL,
            ModTags.Blocks.STORAGE_BLOCKS_CORRUPTED_ARCANE_CRYSTAL,
            ModTags.Blocks.STORAGE_BLOCKS_STELLARITE,
            ModTags.Blocks.STORAGE_BLOCKS_STYGIAN_STEEL
        )

        this.tag(ModTags.Blocks.ORES_ARCANE_CRYSTAL)
            .add(ModBlocks.ARCANE_CRYSTAL_ORE.key(), ModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE.key())
        this.tag(ModTags.Blocks.ORES_RUNIC)
            .add(ModBlocks.RUNIC_STONE.key(), ModBlocks.RUNIC_DEEPSLATE.key(), ModBlocks.RUNIC_DARKSTONE.key())
        this.tag(ModTags.Blocks.ORES_STELLARITE).add(ModBlocks.STELLA_ARCANUM.key())
        this.tag(Tags.Blocks.ORES).addTag(ModTags.Blocks.ORES_ARCANE_CRYSTAL).addTag(ModTags.Blocks.ORES_RUNIC).addTag(
            ModTags.Blocks.ORES_STELLARITE
        )
        this.tag(Tags.Blocks.ORES_IN_GROUND_STONE)
            .add(ModBlocks.ARCANE_CRYSTAL_ORE.key(), ModBlocks.RUNIC_STONE.key(), ModBlocks.STELLA_ARCANUM.key())
        this.tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE).add(
            ModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE.key(),
            ModBlocks.RUNIC_DEEPSLATE.key(),
            ModBlocks.STELLA_ARCANUM.key()
        )
        this.tag(BlockTags.BASE_STONE_OVERWORLD).add(ModBlocks.DARKSTONE.key())

        this.tag(Tags.Blocks.GLASS_BLOCKS).add(ModBlocks.DEORUM_GLASS.key(), ModBlocks.RUNIC_GLASS.key())
        this.tag(Tags.Blocks.GLASS_BLOCKS_COLORLESS).add(ModBlocks.DEORUM_GLASS.key(), ModBlocks.RUNIC_GLASS.key())
        this.tag(Tags.Blocks.GLASS_PANES).add(ModBlocks.DEORUM_GLASS_PANE.key(), ModBlocks.RUNIC_GLASS_PANE.key())
        this.tag(Tags.Blocks.GLASS_PANES_COLORLESS)
            .add(ModBlocks.DEORUM_GLASS_PANE.key(), ModBlocks.RUNIC_GLASS_PANE.key())

        this.tag(BlockTags.CLIMBABLE).add(ModBlocks.EDELWOOD_LADDER.key())
        this.tag(BlockTags.FALL_DAMAGE_RESETTING).add(ModBlocks.EDELWOOD_LADDER.key())

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .add(
                ModBlocks.HEPHAESTUS_FORGE.key(),
                ModBlocks.DEORUM_CHAIN.key(),
                ModBlocks.ASTERITE_DEBRIS.key(),
                ModBlocks.STELLA_ARCANUM.key(),
                ModBlocks.STYGIAN_STEEL_BLOCK.key(),
                ModBlocks.ARCANE_CRYSTAL_BLOCK.key(),
                ModBlocks.CORRUPTED_ARCANE_CRYSTAL_BLOCK.key(),
                ModBlocks.DEORUM_BLOCK.key(),
                ModBlocks.ASTERITE_BLOCK.key(),
                ModBlocks.STELLARITE_BLOCK.key(),
                ModBlocks.SOULLESS_SANDSTONE.key(),
                ModBlocks.CUT_SOULLESS_SANDSTONE.key(),
                ModBlocks.POLISHED_SOULLESS_SANDSTONE.key(),
                ModBlocks.SOULLESS_SANDSTONE_SLAB.key(),
                ModBlocks.CUT_SOULLESS_SANDSTONE_SLAB.key(),
                ModBlocks.POLISHED_SOULLESS_SANDSTONE_SLAB.key(),
                ModBlocks.SOULLESS_SANDSTONE_STAIRS.key(),
                ModBlocks.POLISHED_SOULLESS_SANDSTONE_STAIRS.key(),
                ModBlocks.DEORUM_DOOR.key(),
                ModBlocks.DEORUM_TRAPDOOR.key(),
                ModBlocks.DARKSTONE.key(),
                ModBlocks.DARKSTONE_SLAB.key(),
                ModBlocks.DARKSTONE_STAIRS.key(),
                ModBlocks.POLISHED_DARKSTONE.key(),
                ModBlocks.POLISHED_DARKSTONE_SLAB.key(),
                ModBlocks.POLISHED_DARKSTONE_STAIRS.key(),
                ModBlocks.POLISHED_DARKSTONE_PRESSURE_PLATE.key(),
                ModBlocks.POLISHED_DARKSTONE_BUTTON.key(),
                ModBlocks.CHISELED_POLISHED_DARKSTONE.key(),
                ModBlocks.GILDED_CHISELED_POLISHED_DARKSTONE.key(),
                ModBlocks.POLISHED_DARKSTONE_BRICKS.key(),
                ModBlocks.POLISHED_DARKSTONE_BRICK_SLAB.key(),
                ModBlocks.POLISHED_DARKSTONE_BRICK_STAIRS.key(),
                ModBlocks.CRACKED_POLISHED_DARKSTONE_BRICKS.key(),
                ModBlocks.ARCANE_POLISHED_DARKSTONE.key(),
                ModBlocks.ARCANE_POLISHED_DARKSTONE_SLAB.key(),
                ModBlocks.ARCANE_POLISHED_DARKSTONE_STAIRS.key(),
                ModBlocks.CHISELED_ARCANE_POLISHED_DARKSTONE.key(),
                ModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR.key(),
                ModBlocks.TILED_POLISHED_DARKSTONE_BRICKS.key(),
                ModBlocks.CLIBANO_CORE.key(),
                ModBlocks.CLIBANO_SIDE_HORIZONTAL.key(),
                ModBlocks.CLIBANO_SIDE_VERTICAL.key(),
                ModBlocks.CLIBANO_CORNER.key(),
                ModBlocks.CLIBANO_CENTER.key(),
                ModBlocks.CLIBANO_MAIN_PART.key(),
                ModBlocks.DEORUM_LANTERN.key(),
                ModBlocks.DEORUM_SOUL_LANTERN.key()
            )
            .addTag(ModTags.Blocks.RUNIC_STONES).addTag(ModTags.Blocks.RUNE_BLOCKS)
            .addTag(ModTags.Blocks.ARCANE_CRYSTAL_ORES)
            .addTag(ModTags.Blocks.PEDESTALS)
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.SOULLESS_SAND.key(), ModBlocks.MAGICAL_FARMLAND.key())
        this.tag(BlockTags.SUPPORTS_CROPS).add(ModBlocks.MAGICAL_FARMLAND.key())
        this.tag(BlockTags.GROWS_CROPS).add(ModBlocks.MAGICAL_FARMLAND.key())
        this.tag(BlockTags.SUPPORTS_STEM_CROPS).add(ModBlocks.MAGICAL_FARMLAND.key())
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(ModBlocks.AURUM_LEAVES.key(), ModBlocks.NUGGETY_AURUM_LEAVES.key())
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.EDELWOOD_LADDER.key(), ModBlocks.EDELWOOD_BRANCH.key())

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
            .add(ModBlocks.STYGIAN_STEEL_BLOCK.key(), ModBlocks.STELLA_ARCANUM.key(), ModBlocks.STELLARITE_BLOCK.key(), ModBlocks.HEPHAESTUS_FORGE.key())
            .addTag(ModTags.Blocks.RUNIC_STONES).addTag(ModTags.Blocks.RUNE_BLOCKS)
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.DEORUM_BLOCK.key(), ModBlocks.ASTERITE_BLOCK.key()).addTag(ModTags.Blocks.ARCANE_CRYSTAL_ORES)
        this.tag(BlockTags.NEEDS_STONE_TOOL).addTag(ModTags.Blocks.PEDESTALS).add(
            ModBlocks.DARKSTONE.key(),
            ModBlocks.DARKSTONE_SLAB.key(),
            ModBlocks.DARKSTONE_STAIRS.key(),
            ModBlocks.DARKSTONE_WALL.key(),
            ModBlocks.POLISHED_DARKSTONE.key(),
            ModBlocks.POLISHED_DARKSTONE_SLAB.key(),
            ModBlocks.POLISHED_DARKSTONE_STAIRS.key(),
            ModBlocks.POLISHED_DARKSTONE_WALL.key(),
            ModBlocks.POLISHED_DARKSTONE_PRESSURE_PLATE.key(),
            ModBlocks.POLISHED_DARKSTONE_BUTTON.key(),
            ModBlocks.CHISELED_POLISHED_DARKSTONE.key(),
            ModBlocks.GILDED_CHISELED_POLISHED_DARKSTONE.key(),
            ModBlocks.POLISHED_DARKSTONE_BRICKS.key(),
            ModBlocks.POLISHED_DARKSTONE_BRICK_SLAB.key(),
            ModBlocks.POLISHED_DARKSTONE_BRICK_STAIRS.key(),
            ModBlocks.POLISHED_DARKSTONE_BRICK_WALL.key(),
            ModBlocks.CRACKED_POLISHED_DARKSTONE_BRICKS.key(),
            ModBlocks.ARCANE_POLISHED_DARKSTONE.key(),
            ModBlocks.ARCANE_POLISHED_DARKSTONE_SLAB.key(),
            ModBlocks.ARCANE_POLISHED_DARKSTONE_STAIRS.key(),
            ModBlocks.ARCANE_POLISHED_DARKSTONE_WALL.key(),
            ModBlocks.CHISELED_ARCANE_POLISHED_DARKSTONE.key(),
            ModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR.key(),
            ModBlocks.ASTERITE_DEBRIS.key(),
            ModBlocks.CLIBANO_CORE.key(),
            ModBlocks.CLIBANO_SIDE_HORIZONTAL.key(),
            ModBlocks.CLIBANO_SIDE_VERTICAL.key(),
            ModBlocks.CLIBANO_CORNER.key(),
            ModBlocks.CLIBANO_CENTER.key(),
            ModBlocks.CLIBANO_MAIN_PART.key()
        )
    }
}
