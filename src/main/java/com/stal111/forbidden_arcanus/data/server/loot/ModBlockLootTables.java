package com.stal111.forbidden_arcanus.data.server.loot;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.clibano.AbstractClibanoFrameBlock;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.block.properties.ObeliskPart;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.valhelsia.valhelsia_core.datagen.ValhelsiaBlockLootTables;

import java.util.Set;

/**
 * Mod Block Loot Tables <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.server.loot.ModBlockLootTables
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-02-12
 */
public class ModBlockLootTables extends ValhelsiaBlockLootTables {

    public ModBlockLootTables(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider, ForbiddenArcanus.REGISTRY_MANAGER);
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.DARKSTONE.get());
        this.dropSelf(ModBlocks.DARKSTONE_STAIRS.get());
        this.dropSelf(ModBlocks.DARKSTONE_WALL.get());
        this.dropSelf(ModBlocks.POLISHED_DARKSTONE.get());
        this.dropSelf(ModBlocks.POLISHED_DARKSTONE_STAIRS.get());
        this.dropSelf(ModBlocks.POLISHED_DARKSTONE_WALL.get());
        this.dropSelf(ModBlocks.POLISHED_DARKSTONE_PRESSURE_PLATE.get());
        this.dropSelf(ModBlocks.POLISHED_DARKSTONE_BUTTON.get());
        this.dropSelf(ModBlocks.CHISELED_POLISHED_DARKSTONE.get());
        this.dropSelf(ModBlocks.GILDED_CHISELED_POLISHED_DARKSTONE.get());
        this.dropSelf(ModBlocks.POLISHED_DARKSTONE_BRICKS.get());
        this.dropSelf(ModBlocks.POLISHED_DARKSTONE_BRICK_STAIRS.get());
        this.dropSelf(ModBlocks.POLISHED_DARKSTONE_BRICK_WALL.get());
        this.dropSelf(ModBlocks.CRACKED_POLISHED_DARKSTONE_BRICKS.get());
        this.dropSelf(ModBlocks.TILED_POLISHED_DARKSTONE_BRICKS.get());
        this.dropSelf(ModBlocks.ARCANE_POLISHED_DARKSTONE.get());
        this.dropSelf(ModBlocks.ARCANE_POLISHED_DARKSTONE_STAIRS.get());
        this.dropSelf(ModBlocks.ARCANE_POLISHED_DARKSTONE_WALL.get());
        this.dropSelf(ModBlocks.CHISELED_ARCANE_POLISHED_DARKSTONE.get());
        this.dropSelf(ModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR.get());
        this.dropSelf(ModBlocks.DARKSTONE_PEDESTAL.get());
        this.dropSelf(ModBlocks.MAGNETIZED_DARKSTONE_PEDESTAL.get());
        this.dropSelf(ModBlocks.CLIBANO_CORE.get());
        this.dropSelf(ModBlocks.HEPHAESTUS_FORGE_TIER_1.get());
        this.dropSelf(ModBlocks.HEPHAESTUS_FORGE_TIER_2.get());
        this.dropSelf(ModBlocks.HEPHAESTUS_FORGE_TIER_3.get());
        this.dropSelf(ModBlocks.HEPHAESTUS_FORGE_TIER_4.get());
        this.dropSelf(ModBlocks.HEPHAESTUS_FORGE_TIER_5.get());
        this.dropSelf(ModBlocks.QUANTUM_CORE.get());
        this.dropSelf(ModBlocks.QUANTUM_INJECTOR.get());
        this.dropSelf(ModBlocks.ARCANE_CRYSTAL_BLOCK.get());
        this.dropSelf(ModBlocks.CORRUPTED_ARCANE_CRYSTAL_BLOCK.get());
        this.dropSelf(ModBlocks.RUNE_BLOCK.get());
        this.dropSelf(ModBlocks.DARK_RUNE_BLOCK.get());
        this.dropSelf(ModBlocks.STELLARITE_BLOCK.get());
        this.dropSelf(ModBlocks.DEORUM_BLOCK.get());
        this.dropSelf(ModBlocks.OBSIDIANSTEEL_BLOCK.get());
        this.dropSelf(ModBlocks.DARK_NETHER_STAR_BLOCK.get());
        this.dropSelf(ModBlocks.DEORUM_LANTERN.get());
        this.dropSelf(ModBlocks.DEORUM_SOUL_LANTERN.get());
        this.dropSelf(ModBlocks.SOULLESS_SAND.get());
        this.dropSelf(ModBlocks.SOULLESS_SANDSTONE.get());
        this.dropSelf(ModBlocks.SOULLESS_SANDSTONE_STAIRS.get());
        this.dropSelf(ModBlocks.SOULLESS_SANDSTONE_WALL.get());
        this.dropSelf(ModBlocks.CUT_SOULLESS_SANDSTONE.get());
        this.dropSelf(ModBlocks.POLISHED_SOULLESS_SANDSTONE.get());
        this.dropSelf(ModBlocks.POLISHED_SOULLESS_SANDSTONE_STAIRS.get());
        this.dropSelf(ModBlocks.FUNGYSS.get());
        this.dropSelf(ModBlocks.GROWING_EDELWOOD.get());
        this.dropSelf(ModBlocks.AURUM_SAPLING.get());
        this.dropSelf(ModBlocks.FUNGYSS_STEM.get());
        this.dropSelf(ModBlocks.AURUM_LOG.get());
        this.dropSelf(ModBlocks.EDELWOOD_LOG.get());
        this.dropSelf(ModBlocks.CARVED_EDELWOOD_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_AURUM_LOG.get());
        this.dropSelf(ModBlocks.FUNGYSS_HYPHAE.get());
        this.dropSelf(ModBlocks.AURUM_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_AURUM_WOOD.get());
        this.dropSelf(ModBlocks.FUNGYSS_PLANKS.get());
        this.dropSelf(ModBlocks.AURUM_PLANKS.get());
        this.dropSelf(ModBlocks.EDELWOOD_PLANKS.get());
        this.dropSelf(ModBlocks.ARCANE_EDELWOOD_PLANKS.get());
        this.dropSelf(ModBlocks.FUNGYSS_STAIRS.get());
        this.dropSelf(ModBlocks.AURUM_STAIRS.get());
        this.dropSelf(ModBlocks.EDELWOOD_STAIRS.get());
        this.dropSelf(ModBlocks.DEORUM_DOOR.get());
        this.dropSelf(ModBlocks.FUNGYSS_DOOR.get());
        this.dropSelf(ModBlocks.AURUM_DOOR.get());
        this.dropSelf(ModBlocks.EDELWOOD_DOOR.get());
        this.dropSelf(ModBlocks.ARCANE_EDELWOOD_DOOR.get());
        this.dropSelf(ModBlocks.DEORUM_TRAPDOOR.get());
        this.dropSelf(ModBlocks.FUNGYSS_TRAPDOOR.get());
        this.dropSelf(ModBlocks.AURUM_TRAPDOOR.get());
        this.dropSelf(ModBlocks.EDELWOOD_TRAPDOOR.get());
        this.dropSelf(ModBlocks.ARCANE_EDELWOOD_TRAPDOOR.get());
        this.dropSelf(ModBlocks.FUNGYSS_FENCE.get());
        this.dropSelf(ModBlocks.AURUM_FENCE.get());
        this.dropSelf(ModBlocks.EDELWOOD_FENCE.get());
        this.dropSelf(ModBlocks.FUNGYSS_FENCE_GATE.get());
        this.dropSelf(ModBlocks.AURUM_FENCE_GATE.get());
        this.dropSelf(ModBlocks.EDELWOOD_FENCE_GATE.get());
        this.dropSelf(ModBlocks.EDELWOOD_LADDER.get());
        this.dropSelf(ModBlocks.FUNGYSS_BUTTON.get());
        this.dropSelf(ModBlocks.AURUM_BUTTON.get());
        this.dropSelf(ModBlocks.EDELWOOD_BUTTON.get());
        this.dropSelf(ModBlocks.FUNGYSS_PRESSURE_PLATE.get());
        this.dropSelf(ModBlocks.AURUM_PRESSURE_PLATE.get());
        this.dropSelf(ModBlocks.EDELWOOD_PRESSURE_PLATE.get());
        this.dropSelf(ModBlocks.ARCANE_DRAGON_EGG.get());
        this.dropSelf(ModBlocks.NIPA.get());
        this.dropSelf(ModBlocks.DEORUM_CHAIN.get());
        this.dropSelf(ModBlocks.YELLOW_ORCHID.get());
        this.dropSelf(ModBlocks.GOLDEN_ORCHID.get());
        this.dropSelf(ModBlocks.UTREM_JAR.get());
        this.dropSelf(ModBlocks.FORBIDDENOMICON.get());
        this.dropSelf(ModBlocks.DESK.get());
        this.dropSelf(ModBlocks.RESEARCH_DESK.get());
        this.dropSelf(ModBlocks.OBSIDIAN_SKULL.getSkull());
        this.dropOther(ModBlocks.OBSIDIAN_SKULL.getWallSkull(), ModBlocks.OBSIDIAN_SKULL.getSkull());
        this.dropSelf(ModBlocks.CRACKED_OBSIDIAN_SKULL.getSkull());
        this.dropOther(ModBlocks.CRACKED_OBSIDIAN_SKULL.getWallSkull(), ModBlocks.CRACKED_OBSIDIAN_SKULL.getSkull());
        this.dropSelf(ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getSkull());
        this.dropOther(ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getWallSkull(), ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getSkull());
        this.dropSelf(ModBlocks.FADING_OBSIDIAN_SKULL.getSkull());
        this.dropOther(ModBlocks.FADING_OBSIDIAN_SKULL.getWallSkull(), ModBlocks.FADING_OBSIDIAN_SKULL.getSkull());
        this.dropSelf(ModBlocks.AUREALIC_OBSIDIAN_SKULL.getSkull());
        this.dropOther(ModBlocks.AUREALIC_OBSIDIAN_SKULL.getWallSkull(), ModBlocks.AUREALIC_OBSIDIAN_SKULL.getSkull());
        this.dropSelf(ModBlocks.ETERNAL_OBSIDIAN_SKULL.getSkull());
        this.dropOther(ModBlocks.ETERNAL_OBSIDIAN_SKULL.getWallSkull(), ModBlocks.ETERNAL_OBSIDIAN_SKULL.getSkull());

        this.dropOther(ModBlocks.MAGICAL_FARMLAND.get(), Blocks.DIRT);

        this.add(ModBlocks.DARKSTONE_SLAB.get(), this::createSlabItemTable);
        this.add(ModBlocks.POLISHED_DARKSTONE_SLAB.get(), this::createSlabItemTable);
        this.add(ModBlocks.POLISHED_DARKSTONE_BRICK_SLAB.get(), this::createSlabItemTable);
        this.add(ModBlocks.ARCANE_POLISHED_DARKSTONE_SLAB.get(), this::createSlabItemTable);
        this.add(ModBlocks.SOULLESS_SANDSTONE_SLAB.get(), this::createSlabItemTable);
        this.add(ModBlocks.CUT_SOULLESS_SANDSTONE_SLAB.get(), this::createSlabItemTable);
        this.add(ModBlocks.POLISHED_SOULLESS_SANDSTONE_SLAB.get(), this::createSlabItemTable);
        this.add(ModBlocks.FUNGYSS_SLAB.get(), this::createSlabItemTable);
        this.add(ModBlocks.AURUM_SLAB.get(), this::createSlabItemTable);
        this.add(ModBlocks.EDELWOOD_SLAB.get(), this::createSlabItemTable);

        this.dropWhenSilkTouch(ModBlocks.DEORUM_GLASS.get());
        this.dropWhenSilkTouch(ModBlocks.DEORUM_GLASS_PANE.get());
        this.dropWhenSilkTouch(ModBlocks.RUNIC_GLASS.get());
        this.dropWhenSilkTouch(ModBlocks.RUNIC_GLASS_PANE.get());
        this.dropWhenSilkTouch(ModBlocks.DARK_RUNIC_GLASS.get());
        this.dropWhenSilkTouch(ModBlocks.DARK_RUNIC_GLASS_PANE.get());

        this.add(ModBlocks.ARCANE_CRYSTAL_ORE.get(), block -> this.createOreDrop(block, ModItems.ARCANE_CRYSTAL.get()));
        this.add(ModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE.get(), block -> this.createOreDrop(block, ModItems.ARCANE_CRYSTAL.get()));
        this.add(ModBlocks.RUNIC_STONE.get(), block -> this.createOreDrop(block, ModItems.RUNE.get()));
        this.add(ModBlocks.RUNIC_DEEPSLATE.get(), block -> this.createOreDrop(block, ModItems.RUNE.get()));
        this.add(ModBlocks.RUNIC_DARKSTONE.get(), block -> this.createOreDrop(block, ModItems.RUNE.get()));
        this.add(ModBlocks.STELLA_ARCANUM.get(), block -> this.createSingleItemTableWithSilkTouch(block, ModItems.STELLARITE_PIECE.get()));
        this.add(ModBlocks.FUNGYSS_BLOCK.get(), block -> this.createMushroomBlockDrop(block, ModBlocks.FUNGYSS.get()));
        this.add(ModBlocks.ARCANE_CRYSTAL_OBELISK.get(), block -> this.createSinglePropConditionTable(block, ModBlockStateProperties.OBELISK_PART, ObeliskPart.LOWER));
        this.add(ModBlocks.CORRUPTED_ARCANE_CRYSTAL_OBELISK.get(), block -> this.createSinglePropConditionTable(block, ModBlockStateProperties.OBELISK_PART, ObeliskPart.LOWER));
        this.add(ModBlocks.AURUM_LEAVES.get(), block -> this.createLeavesDrops(block, ModBlocks.AURUM_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(ModBlocks.NUGGETY_AURUM_LEAVES.get(), block -> this.createNuggetyAurumLeavesDrops(block, ModBlocks.AURUM_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(ModBlocks.ESSENCE_UTREM_JAR.get(), this::createUtremJarDrops);
        this.add(ModBlocks.CLIBANO_CENTER.get(), this::createClibanoFrameTable);
        this.add(ModBlocks.CLIBANO_CORNER.get(), this::createClibanoFrameTable);
        this.add(ModBlocks.CLIBANO_SIDE_HORIZONTAL.get(), this::createClibanoFrameTable);
        this.add(ModBlocks.CLIBANO_SIDE_VERTICAL.get(), this::createClibanoFrameTable);
        this.add(ModBlocks.CLIBANO_MAIN_PART.get(), block -> LootTable.lootTable());
        this.add(ModBlocks.EDELWOOD_BRANCH.get(), block -> this.createSingleItemTableWithSilkTouch(block, ModItems.EDELWOOD_STICK.get()));
        this.add(ModBlocks.BLACK_HOLE.get(), block -> LootTable.lootTable());
        this.add(ModBlocks.WHIRLWIND.get(), block -> LootTable.lootTable());
        this.add(ModBlocks.UPWIND.get(), block -> LootTable.lootTable());

        this.dropPottedContents(ModBlocks.POTTED_FUNGYSS.get());
        this.dropPottedContents(ModBlocks.POTTED_AURUM_SAPLING.get());
        this.dropPottedContents(ModBlocks.POTTED_GROWING_EDELWOOD.get());
        this.dropPottedContents(ModBlocks.POTTED_YELLOW_ORCHID.get());
    }

    private LootItemCondition.Builder hasShearsOrSilkTouch() {
        return HAS_SHEARS.or(this.hasSilkTouch());
    }

    private LootItemCondition.Builder doesNotHaveShearsOrSilkTouch() {
        return this.hasShearsOrSilkTouch().invert();
    }

    private LootTable.Builder createNuggetyAurumLeavesDrops(Block leaves, Block sapling, float... chances) {
        HolderLookup.RegistryLookup<Enchantment> registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        return this.createLeavesDrops(leaves, sapling, chances)
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(this.doesNotHaveShearsOrSilkTouch())
                        .add(this.applyExplosionCondition(leaves, LootItem.lootTableItem(Items.GOLD_NUGGET)
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(registryLookup.getOrThrow(Enchantments.FORTUNE), 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))
                        ))
                );
    }

    private LootTable.Builder createUtremJarDrops(Block block) {
        return LootTable.lootTable()
                .withPool(this.applyExplosionCondition(block, LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(block)
                                .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                                        .include(ModDataComponents.ESSENCE_STORAGE.get()))
                        ))
                );
    }

    private LootTable.Builder createClibanoFrameTable(Block block) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(DynamicLoot.dynamicEntry(AbstractClibanoFrameBlock.DYNAMIC_DROP_ID))
                );
    }
}
