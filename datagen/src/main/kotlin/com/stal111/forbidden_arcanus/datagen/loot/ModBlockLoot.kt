package com.stal111.forbidden_arcanus.datagen.loot

import com.stal111.forbidden_arcanus.common.block.clibano.AbstractClibanoFrameBlock
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties
import com.stal111.forbidden_arcanus.common.block.properties.ObeliskPart
import com.stal111.forbidden_arcanus.core.init.ModBlocks
import com.stal111.forbidden_arcanus.core.init.ModDataComponents
import com.stal111.forbidden_arcanus.core.init.ModItems
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.loot.BlockLootSubProvider
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.DynamicLoot
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction
import net.minecraft.world.level.storage.loot.parameters.LootContextParams
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue

class ModBlockLoot(
    lookupProvider: HolderLookup.Provider,
    val blocks: List<() -> Block>
) : BlockLootSubProvider(setOf<Item>(), FeatureFlags.DEFAULT_FLAGS, lookupProvider) {

    override fun generate() {
        dropSelf(ModBlocks.DARKSTONE.get())
        dropSelf(ModBlocks.DARKSTONE_STAIRS.get())
        dropSelf(ModBlocks.DARKSTONE_WALL.get())
        dropSelf(ModBlocks.POLISHED_DARKSTONE.get())
        dropSelf(ModBlocks.POLISHED_DARKSTONE_STAIRS.get())
        dropSelf(ModBlocks.POLISHED_DARKSTONE_WALL.get())
        dropSelf(ModBlocks.POLISHED_DARKSTONE_PRESSURE_PLATE.get())
        dropSelf(ModBlocks.POLISHED_DARKSTONE_BUTTON.get())
        dropSelf(ModBlocks.CHISELED_POLISHED_DARKSTONE.get())
        dropSelf(ModBlocks.GILDED_CHISELED_POLISHED_DARKSTONE.get())
        dropSelf(ModBlocks.POLISHED_DARKSTONE_BRICKS.get())
        dropSelf(ModBlocks.POLISHED_DARKSTONE_BRICK_STAIRS.get())
        dropSelf(ModBlocks.POLISHED_DARKSTONE_BRICK_WALL.get())
        dropSelf(ModBlocks.CRACKED_POLISHED_DARKSTONE_BRICKS.get())
        dropSelf(ModBlocks.TILED_POLISHED_DARKSTONE_BRICKS.get())
        dropSelf(ModBlocks.ARCANE_POLISHED_DARKSTONE.get())
        dropSelf(ModBlocks.ARCANE_POLISHED_DARKSTONE_STAIRS.get())
        dropSelf(ModBlocks.ARCANE_POLISHED_DARKSTONE_WALL.get())
        dropSelf(ModBlocks.CHISELED_ARCANE_POLISHED_DARKSTONE.get())
        dropSelf(ModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR.get())
        dropSelf(ModBlocks.DARKSTONE_PEDESTAL.get())
        dropSelf(ModBlocks.MAGNETIZED_DARKSTONE_PEDESTAL.get())
//        dropSelf(ModBlocks.CLIBANO_CORE.get())
        dropSelf(ModBlocks.HEPHAESTUS_FORGE.get())
        dropSelf(ModBlocks.QUANTUM_CORE.get())
        dropSelf(ModBlocks.QUANTUM_INJECTOR.get())
        dropSelf(ModBlocks.ARCANE_CRYSTAL_BLOCK.get())
        dropSelf(ModBlocks.CORRUPTED_ARCANE_CRYSTAL_BLOCK.get())
        dropSelf(ModBlocks.RUNE_BLOCK.get())
        dropSelf(ModBlocks.STELLARITE_BLOCK.get())
        dropSelf(ModBlocks.DEORUM_BLOCK.get())
        dropSelf(ModBlocks.OBSIDIANSTEEL_BLOCK.get())
        dropSelf(ModBlocks.DEORUM_LANTERN.get())
        dropSelf(ModBlocks.DEORUM_SOUL_LANTERN.get())
        dropSelf(ModBlocks.SOULLESS_SAND.get())
        dropSelf(ModBlocks.SOULLESS_SANDSTONE.get())
        dropSelf(ModBlocks.SOULLESS_SANDSTONE_STAIRS.get())
        dropSelf(ModBlocks.SOULLESS_SANDSTONE_WALL.get())
        dropSelf(ModBlocks.CUT_SOULLESS_SANDSTONE.get())
        dropSelf(ModBlocks.POLISHED_SOULLESS_SANDSTONE.get())
        dropSelf(ModBlocks.POLISHED_SOULLESS_SANDSTONE_STAIRS.get())
        dropSelf(ModBlocks.FUNGYSS.get())
        dropSelf(ModBlocks.GROWING_EDELWOOD.get())
        dropSelf(ModBlocks.AURUM_SAPLING.get())
        dropSelf(ModBlocks.FUNGYSS_STEM.get())
        dropSelf(ModBlocks.AURUM_LOG.get())
        dropSelf(ModBlocks.EDELWOOD_LOG.get())
        dropSelf(ModBlocks.CARVED_EDELWOOD_LOG.get())
        dropSelf(ModBlocks.STRIPPED_AURUM_LOG.get())
        dropSelf(ModBlocks.FUNGYSS_HYPHAE.get())
        dropSelf(ModBlocks.AURUM_WOOD.get())
        dropSelf(ModBlocks.STRIPPED_AURUM_WOOD.get())
        dropSelf(ModBlocks.FUNGYSS_PLANKS.get())
        dropSelf(ModBlocks.AURUM_PLANKS.get())
        dropSelf(ModBlocks.EDELWOOD_PLANKS.get())
        dropSelf(ModBlocks.ARCANE_EDELWOOD_PLANKS.get())
        dropSelf(ModBlocks.FUNGYSS_STAIRS.get())
        dropSelf(ModBlocks.AURUM_STAIRS.get())
        dropSelf(ModBlocks.EDELWOOD_STAIRS.get())
        add(ModBlocks.DEORUM_DOOR.get()) {
            createSinglePropConditionTable(it, BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)
        }
        add(ModBlocks.FUNGYSS_DOOR.get()) {
            createSinglePropConditionTable(it, BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)
        }
        add(ModBlocks.AURUM_DOOR.get()) {
            createSinglePropConditionTable(it, BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)
        }
        add(ModBlocks.EDELWOOD_DOOR.get()) {
            createSinglePropConditionTable(it, BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)
        }
        add(ModBlocks.ARCANE_EDELWOOD_DOOR.get()) {
            createSinglePropConditionTable(it, BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)
        }
        dropSelf(ModBlocks.DEORUM_TRAPDOOR.get())
        dropSelf(ModBlocks.FUNGYSS_TRAPDOOR.get())
        dropSelf(ModBlocks.AURUM_TRAPDOOR.get())
        dropSelf(ModBlocks.EDELWOOD_TRAPDOOR.get())
        dropSelf(ModBlocks.ARCANE_EDELWOOD_TRAPDOOR.get())
        dropSelf(ModBlocks.FUNGYSS_FENCE.get())
        dropSelf(ModBlocks.AURUM_FENCE.get())
        dropSelf(ModBlocks.EDELWOOD_FENCE.get())
        dropSelf(ModBlocks.FUNGYSS_FENCE_GATE.get())
        dropSelf(ModBlocks.AURUM_FENCE_GATE.get())
        dropSelf(ModBlocks.EDELWOOD_FENCE_GATE.get())
        dropSelf(ModBlocks.EDELWOOD_LADDER.get())
        dropSelf(ModBlocks.FUNGYSS_BUTTON.get())
        dropSelf(ModBlocks.AURUM_BUTTON.get())
        dropSelf(ModBlocks.EDELWOOD_BUTTON.get())
        dropSelf(ModBlocks.FUNGYSS_PRESSURE_PLATE.get())
        dropSelf(ModBlocks.AURUM_PRESSURE_PLATE.get())
        dropSelf(ModBlocks.EDELWOOD_PRESSURE_PLATE.get())
        dropSelf(ModBlocks.ARCANE_DRAGON_EGG.get())
        dropSelf(ModBlocks.DEORUM_CHAIN.get())
        dropSelf(ModBlocks.YELLOW_ORCHID.get())
        dropSelf(ModBlocks.UTREM_JAR.get())


        //dropSelf(ModBlocks.FORBIDDENOMICON.get());
        //dropSelf(ModBlocks.DESK.get());
        //dropSelf(ModBlocks.RESEARCH_DESK.get());
        dropSelf(ModBlocks.OBSIDIAN_SKULL.getSkull())
        dropOther(ModBlocks.OBSIDIAN_SKULL.getWallSkull(), ModBlocks.OBSIDIAN_SKULL.getSkull())
        dropSelf(ModBlocks.CRACKED_OBSIDIAN_SKULL.getSkull())
        dropOther(ModBlocks.CRACKED_OBSIDIAN_SKULL.getWallSkull(), ModBlocks.CRACKED_OBSIDIAN_SKULL.getSkull())
        dropSelf(ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getSkull())
        dropOther(ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getWallSkull(), ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getSkull())
        dropSelf(ModBlocks.FADING_OBSIDIAN_SKULL.getSkull())
        dropOther(ModBlocks.FADING_OBSIDIAN_SKULL.getWallSkull(), ModBlocks.FADING_OBSIDIAN_SKULL.getSkull())


        //        dropSelf(ModBlocks.AUREALIC_OBSIDIAN_SKULL.getSkull());
//        dropOther(ModBlocks.AUREALIC_OBSIDIAN_SKULL.getWallSkull(), ModBlocks.AUREALIC_OBSIDIAN_SKULL.getSkull());
//        dropSelf(ModBlocks.ETERNAL_OBSIDIAN_SKULL.getSkull());
//        dropOther(ModBlocks.ETERNAL_OBSIDIAN_SKULL.getWallSkull(), ModBlocks.ETERNAL_OBSIDIAN_SKULL.getSkull());
        dropOther(ModBlocks.MAGICAL_FARMLAND.get(), Blocks.DIRT)

        add(ModBlocks.DARKSTONE_SLAB.get()) { createSlabItemTable(it) }
        add(ModBlocks.POLISHED_DARKSTONE_SLAB.get()) { createSlabItemTable(it) }
        add(ModBlocks.POLISHED_DARKSTONE_BRICK_SLAB.get()) { createSlabItemTable(it) }
        add(ModBlocks.ARCANE_POLISHED_DARKSTONE_SLAB.get()) { createSlabItemTable(it) }
        add(ModBlocks.SOULLESS_SANDSTONE_SLAB.get()) { createSlabItemTable(it) }
        add(ModBlocks.CUT_SOULLESS_SANDSTONE_SLAB.get()) { createSlabItemTable(it) }
        add(ModBlocks.POLISHED_SOULLESS_SANDSTONE_SLAB.get()) { createSlabItemTable(it) }
        add(ModBlocks.FUNGYSS_SLAB.get()) { createSlabItemTable(it) }
        add(ModBlocks.AURUM_SLAB.get()) { createSlabItemTable(it) }
        add(ModBlocks.EDELWOOD_SLAB.get()) { createSlabItemTable(it) }

        dropWhenSilkTouch(ModBlocks.DEORUM_GLASS.get())
        dropWhenSilkTouch(ModBlocks.DEORUM_GLASS_PANE.get())
        dropWhenSilkTouch(ModBlocks.RUNIC_GLASS.get())
        dropWhenSilkTouch(ModBlocks.RUNIC_GLASS_PANE.get())

        add(ModBlocks.ARCANE_CRYSTAL_ORE.get()) { createOreDrop(it, ModItems.ARCANE_CRYSTAL.get()) }
        add(ModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE.get()) { createOreDrop(it, ModItems.ARCANE_CRYSTAL.get()) }
        add(ModBlocks.RUNIC_STONE.get()) { createOreDrop(it, ModItems.RUNE.get()) }
        add(ModBlocks.RUNIC_DEEPSLATE.get()) { createOreDrop(it, ModItems.RUNE.get()) }
        add(ModBlocks.RUNIC_DARKSTONE.get()) { createOreDrop(it, ModItems.RUNE.get()) }
        add(ModBlocks.STELLA_ARCANUM.get()) {
            createSingleItemTableWithSilkTouch(it, ModItems.STELLARITE_PIECE.get())
        }
        add(ModBlocks.FUNGYSS_BLOCK.get()) { createMushroomBlockDrop(it, ModBlocks.FUNGYSS.get()) }
        add(ModBlocks.ARCANE_CRYSTAL_OBELISK.get()) {
            createSinglePropConditionTable(it, ModBlockStateProperties.OBELISK_PART, ObeliskPart.LOWER)
        }
        add(ModBlocks.CORRUPTED_ARCANE_CRYSTAL_OBELISK.get()) {
            createSinglePropConditionTable(it, ModBlockStateProperties.OBELISK_PART, ObeliskPart.LOWER)
        }
        add(ModBlocks.AURUM_LEAVES.get()) {
            createLeavesDrops(
                it,
                ModBlocks.AURUM_SAPLING.get(),
                *NORMAL_LEAVES_SAPLING_CHANCES
            )
        }
        add(ModBlocks.NUGGETY_AURUM_LEAVES.get()) {
            createNuggetyAurumLeavesDrops(
                it,
                ModBlocks.AURUM_SAPLING.get(),
                *NORMAL_LEAVES_SAPLING_CHANCES
            )
        }
        add(ModBlocks.ESSENCE_UTREM_JAR.get()) { createUtremJarDrops(it) }
        add(ModBlocks.CLIBANO_CENTER.get()) { createClibanoFrameTable() }
        add(ModBlocks.CLIBANO_CORNER.get()) { createClibanoFrameTable() }
        add(ModBlocks.CLIBANO_SIDE_HORIZONTAL.get()) { createClibanoFrameTable() }
        add(ModBlocks.CLIBANO_SIDE_VERTICAL.get()) { createClibanoFrameTable() }
        add(ModBlocks.CLIBANO_MAIN_PART.get()) { noDrop() }
        add(ModBlocks.EDELWOOD_BRANCH.get()) { createSingleItemTableWithSilkTouch(it, ModItems.EDELWOOD_STICK.get()) }
        add(ModBlocks.BLACK_HOLE.get()) { noDrop() }
        add(ModBlocks.WHIRLWIND.get()) { noDrop() }
        add(ModBlocks.UPWIND.get()) { noDrop() }

        dropPottedContents(ModBlocks.POTTED_FUNGYSS.get())
        dropPottedContents(ModBlocks.POTTED_AURUM_SAPLING.get())
        dropPottedContents(ModBlocks.POTTED_GROWING_EDELWOOD.get())
        dropPottedContents(ModBlocks.POTTED_YELLOW_ORCHID.get())
    }

    private fun hasShearsOrSilkTouch() = this.hasShears().or(this.hasSilkTouch())

    private fun doesNotHaveShearsOrSilkTouch() = this.hasShearsOrSilkTouch().invert()

    private fun createNuggetyAurumLeavesDrops(leaves: Block, sapling: Block, vararg chances: Float): LootTable.Builder {
        val registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT)

        return this.createLeavesDrops(leaves, sapling, *chances).withPool(
            LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .`when`(this.doesNotHaveShearsOrSilkTouch())
                .add(
                    this.applyExplosionCondition(
                        leaves, LootItem.lootTableItem(Items.GOLD_NUGGET).`when`(
                            BonusLevelTableCondition.bonusLevelFlatChance(
                                registryLookup.getOrThrow(Enchantments.FORTUNE),
                                0.005f, 0.0055555557f, 0.00625f, 0.008333334f, 0.025f
                            )
                        )
                    )
                )
        )
    }

    private fun createUtremJarDrops(block: Block) = LootTable.lootTable().withPool(
        this.applyExplosionCondition(
            block, LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(
                    LootItem.lootTableItem(block).apply(
                        CopyComponentsFunction
                            .copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY)
                            .include(ModDataComponents.ESSENCE_STORAGE.get())
                    )
                )
        )
    )

    private fun createClibanoFrameTable() = LootTable.lootTable().withPool(
        LootPool.lootPool()
            .setRolls(ConstantValue.exactly(1f))
            .add(DynamicLoot.dynamicEntry(AbstractClibanoFrameBlock.DYNAMIC_DROP_ID))
    )

    override fun getKnownBlocks() = blocks.map { it() }
}
