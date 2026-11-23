package com.stal111.forbidden_arcanus.datagen.tags

import com.stal111.forbidden_arcanus.core.init.ModBlocks
import com.stal111.forbidden_arcanus.core.init.ModItems
import com.stal111.forbidden_arcanus.util.ModTags
import net.minecraft.core.HolderLookup
import net.minecraft.references.BlockItemIds
import net.minecraft.references.ItemIds
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.neoforged.neoforge.common.Tags
import net.valhelsia.dataforge.DataProviderContext
import net.valhelsia.dataforge.tag.DataForgeItemTagsProvider

class ModItemTagsProvider(context: DataProviderContext.Server) : DataForgeItemTagsProvider(context) {
    override fun addTags(provider: HolderLookup.Provider) {
        this.tag(ModTags.Items.OBSIDIAN_SKULLS).add(
            ModBlocks.OBSIDIAN_SKULL.itemEntry!!.key(),
            ModBlocks.CRACKED_OBSIDIAN_SKULL.itemEntry!!.key(),
            ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.itemEntry!!.key(),
            ModBlocks.FADING_OBSIDIAN_SKULL.itemEntry!!.key(),
            ModBlocks.AUREALIC_OBSIDIAN_SKULL.itemEntry!!.key(),
            ModBlocks.ETERNAL_OBSIDIAN_SKULL.itemEntry!!.key()
        )
        // this.tag(Tags.Items.HEADS).addTag(ModTags.Items.OBSIDIAN_SKULLS);
        this.tag(ModTags.Items.BLACKSMITH_GAVEL).add(
            ModItems.WOODEN_BLACKSMITH_GAVEL.key(),
            ModItems.STONE_BLACKSMITH_GAVEL.key(),
            ModItems.GOLDEN_BLACKSMITH_GAVEL.key(),
            ModItems.IRON_BLACKSMITH_GAVEL.key(),
            ModItems.DIAMOND_BLACKSMITH_GAVEL.key(),
            ModItems.NETHERITE_BLACKSMITH_GAVEL.key()
        //                ModItems.REINFORCED_DEORUM_BLACKSMITH_GAVEL.key()
        )
        this.tag(ModTags.Items.DEORUM_INGOTS).add(ModItems.DEORUM_INGOT.key())
        this.tag(ModTags.Items.DEORUM_NUGGETS).add(ModItems.DEORUM_NUGGET.key())
        this.tag(ModTags.Items.STYGIAN_STEEL_INGOTS).add(ModItems.STYGIAN_STEEL_INGOT.key())
        this.tag(ModTags.Items.MAGICAL_FARMLAND_BLACKLISTED)
            .add(BlockItemIds.WHEAT_CROP.item, BlockItemIds.BEETROOT_CROP.item, BlockItemIds.MELON_CROP.item, BlockItemIds.PUMPKIN_CROP.item, BlockItemIds.TORCHFLOWER_CROP.item, BlockItemIds.PITCHER_CROP.item)
        this.tag(Tags.Items.INGOTS).addTags(ModTags.Items.DEORUM_INGOTS, ModTags.Items.STYGIAN_STEEL_INGOTS)
        //        this.tag(ItemTags.PICKAXES).add(ModItems.DRACO_ARCANUS_PICKAXE.key(), ModItems.REINFORCED_DEORUM_PICKAXE.key());
        this.tag(ItemTags.ARROWS).add(ModItems.BOOM_ARROW.key(), ModItems.DRACO_ARCANUS_ARROW.key())
        this.tag(ModTags.Items.BLACK_HOLE_UNAFFECTED).add(
            ModItems.STELLARITE_PIECE.key(),
            ModBlocks.STELLARITE_BLOCK.itemEntry!!.key(),
            ModItems.HEPHAESTUS_FORGE.key(),
            ModBlocks.STELLA_ARCANUM.itemEntry!!.key(),
            ModItems.ETERNAL_STELLA.key(),
            ModItems.DARK_NETHER_STAR.key(),
            ItemIds.NETHER_STAR,
            BlockItemIds.BEDROCK.item,
            BlockItemIds.OBSIDIAN.item,
            BlockItemIds.CRYING_OBSIDIAN.item,
            BlockItemIds.ENCHANTING_TABLE.item,
            BlockItemIds.END_PORTAL_FRAME.item,
            BlockItemIds.ENDER_CHEST.item,
            BlockItemIds.RESPAWN_ANCHOR.item
        )
        this.tag(ModTags.Items.EXPLOSION_RESISTANT).add(
            ItemIds.NETHER_STAR,
            ModItems.DARK_NETHER_STAR.key(),
            ModItems.STELLARITE_PIECE.key(),
            ModItems.ETERNAL_STELLA.key(),
            ModBlocks.STELLARITE_BLOCK.itemEntry!!.key()
        )
        this.tag(Tags.Items.MUSHROOMS).add(ModBlocks.FUNGYSS.itemEntry!!.key())
        this.tag(ItemTags.HEAD_ARMOR).add(ModItems.DRACO_ARCANUS_HELMET.key(), ModItems.TYR_HELMET.key())
        this.tag(ItemTags.CHEST_ARMOR).add(ModItems.DRACO_ARCANUS_CHESTPLATE.key(), ModItems.TYR_CHESTPLATE.key())
        this.tag(ItemTags.LEG_ARMOR).add(ModItems.DRACO_ARCANUS_LEGGINGS.key(), ModItems.TYR_LEGGINGS.key())
        this.tag(ItemTags.FOOT_ARMOR).add(ModItems.DRACO_ARCANUS_BOOTS.key(), ModItems.TYR_BOOTS.key())

        tag(ModTags.Items.RECEIVES_BLOOD).add(ModItems.BLOOD_TEST_TUBE.key())
        tag(ModTags.Items.ESSENCE_TRANSFORMS_INTO_GROWING_EDELWOOD).add(BlockItemIds.DEAD_BUSH.item, BlockItemIds.DARK_OAK_SAPLING.item, BlockItemIds.PALE_OAK_SAPLING.item)
        tag(ModTags.Items.ESSENCE_TRANSFORMS_INTO_AURUM_SAPLING).add(BlockItemIds.OAK_SAPLING.item, BlockItemIds.BIRCH_SAPLING.item)
        tag(ModTags.Items.ESSENCE_TRANSFORMS_INTO_FUNGYSS).add(BlockItemIds.BROWN_MUSHROOM.item, BlockItemIds.RED_MUSHROOM.item)

        this.tag(ModTags.Items.ETERNAL_INCOMPATIBLE).add(ItemIds.ELYTRA)
        this.tag(ModTags.Items.FIERY_INCOMPATIBLE)
        this.tag(ModTags.Items.MAGNETIZED_INCOMPATIBLE)
        this.tag(ModTags.Items.DEMOLISHING_INCOMPATIBLE)
        this.tag(ModTags.Items.AQUATIC_INCOMPATIBLE)
        this.tag(ModTags.Items.SOULBOUND_INCOMPATIBLE)

        this.tag(ModTags.Items.SOULBOUND_APPLICABLE).addTags(Tags.Items.TOOLS, Tags.Items.ARMORS)

        this.tag(ItemTags.STONE_TOOL_MATERIALS).add(ModBlocks.DARKSTONE.itemEntry!!.key())
        this.tag(ItemTags.STONE_CRAFTING_MATERIALS).add(ModBlocks.DARKSTONE.itemEntry!!.key())

        this.tag(ModTags.Items.GEMS_ARCANE_CRYSTAL).add(ModItems.ARCANE_CRYSTAL.key())
        this.tag(ModTags.Items.GEMS_CORRUPTED_ARCANE_CRYSTAL).add(ModItems.CORRUPTED_ARCANE_CRYSTAL.key())
        this.tag(Tags.Items.GEMS).addTag(ModTags.Items.GEMS_ARCANE_CRYSTAL)
            .addTag(ModTags.Items.GEMS_CORRUPTED_ARCANE_CRYSTAL)

        this.tag(ModTags.Items.NUGGETS_DEORUM).add(ModItems.DEORUM_NUGGET.key())
        this.tag(Tags.Items.NUGGETS).addTag(ModTags.Items.NUGGETS_DEORUM)

        this.tag(ModTags.Items.DUSTS_ARCANE_CRYSTAL).add(ModItems.ARCANE_CRYSTAL_DUST.key())
        this.tag(ModTags.Items.DUSTS_MUNDABITUR).add(ModItems.MUNDABITUR_DUST.key())
        this.tag(ModTags.Items.DUSTS_CORRUPTI).add(ModItems.CORRUPTI_DUST.key())
        this.tag(Tags.Items.DUSTS).addTag(ModTags.Items.DUSTS_ARCANE_CRYSTAL).addTag(ModTags.Items.DUSTS_MUNDABITUR)
            .addTag(
                ModTags.Items.DUSTS_CORRUPTI
            )

        //        this.tag(ItemTags.BOATS).add(ModItems.AURUM_BOAT.key(), ModItems.EDELWOOD_BOAT.key());
//        this.tag(ItemTags.CHEST_BOATS).add(ModItems.AURUM_CHEST_BOAT.key(), ModItems.EDELWOOD_CHEST_BOAT.key());
        this.copy(ModTags.Blocks.FUNGYSS_STEMS, ModTags.Items.FUNGYSS_STEMS)
        this.copy(ModTags.Blocks.MYSTERYWOOD_LOGS, ModTags.Items.MYSTERYWOOD_LOGS)
        this.copy(ModTags.Blocks.EDELWOOD_LOGS, ModTags.Items.EDELWOOD_LOGS)
        this.copy(BlockTags.LOGS, ItemTags.LOGS)
        this.copy(BlockTags.PLANKS, ItemTags.PLANKS)
        this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS)
        this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS)
        this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES)
        this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS)
//        this.copy(BlockTags.BUTTONS, ItemTags.BUTTONS)
        this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES)
        this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS)
//        this.copy(BlockTags.DOORS, ItemTags.DOORS)
        this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS)
//        this.copy(BlockTags.TRAPDOORS, ItemTags.TRAPDOORS)
        this.copy(Tags.Blocks.FENCES_WOODEN, Tags.Items.FENCES_WOODEN)
        this.copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN)
        this.copy(ModTags.Blocks.RUNIC_STONES, ModTags.Items.RUNIC_STONES)
        this.copy(ModTags.Blocks.RUNE_BLOCKS, ModTags.Items.RUNE_BLOCKS)
        this.copy(ModTags.Blocks.ARCANE_CRYSTAL_ORES, ModTags.Items.ARCANE_CRYSTAL_ORES)
        //this.copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
//        this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS)
        this.copy(BlockTags.LEAVES, ItemTags.LEAVES)
//        this.copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS)
        this.copy(ModTags.Blocks.STORAGE_BLOCKS_DEORUM, ModTags.Items.STORAGE_BLOCKS_DEORUM)
        this.copy(ModTags.Blocks.STORAGE_BLOCKS_ARCANE_CRYSTAL, ModTags.Items.STORAGE_BLOCKS_ARCANE_CRYSTAL)
        this.copy(
            ModTags.Blocks.STORAGE_BLOCKS_CORRUPTED_ARCANE_CRYSTAL,
            ModTags.Items.STORAGE_BLOCKS_CORRUPTED_ARCANE_CRYSTAL
        )
        this.copy(ModTags.Blocks.STORAGE_BLOCKS_STELLARITE, ModTags.Items.STORAGE_BLOCKS_STELLARITE)
        this.copy(ModTags.Blocks.STORAGE_BLOCKS_STYGIAN_STEEL, ModTags.Items.STORAGE_BLOCKS_STYGIAN_STEEL)
        this.copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS)
        this.copy(ModTags.Blocks.ORES_ARCANE_CRYSTAL, ModTags.Items.ORES_ARCANE_CRYSTAL)
        this.copy(ModTags.Blocks.ORES_RUNIC, ModTags.Items.ORES_RUNIC)
        this.copy(ModTags.Blocks.ORES_STELLARITE, ModTags.Items.ORES_STELLARITE)
        this.copy(Tags.Blocks.ORES, Tags.Items.ORES)
        this.copy(Tags.Blocks.ORES_IN_GROUND_STONE, Tags.Items.ORES_IN_GROUND_STONE)
        this.copy(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE, Tags.Items.ORES_IN_GROUND_DEEPSLATE)
        this.copy(Tags.Blocks.GLASS_BLOCKS, Tags.Items.GLASS_BLOCKS)
        this.copy(Tags.Blocks.GLASS_BLOCKS_COLORLESS, Tags.Items.GLASS_BLOCKS_COLORLESS)
        this.copy(Tags.Blocks.GLASS_PANES, Tags.Items.GLASS_PANES)
        this.copy(Tags.Blocks.GLASS_PANES_COLORLESS, Tags.Items.GLASS_PANES_COLORLESS)
    }
}
